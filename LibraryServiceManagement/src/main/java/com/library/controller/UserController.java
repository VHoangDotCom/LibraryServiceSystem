package com.library.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entity.Order;
import com.library.entity.Role;
import com.library.entity.User;
import com.library.entity.email.MailRequest;
import com.library.model.PasswordModel;
import com.library.model.RoleToUserModel;
import com.library.repository.UserRepository;
import com.library.service.MailService;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@EnableScheduling
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        if(userService.getUser(username) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username "+username+" is not existed !");
        }else {
            return ResponseEntity.ok().body(userService.getUser(username));
        }
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        if(userService.findUserByEmail(email) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email "+email+" is not existed !");
        }else {
            return ResponseEntity.ok().body(userService.findUserByEmail(email));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable Long id){
        if(userRepository.findById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id "+id+" is not existed !");
        }else {
            return ResponseEntity.ok().body(userRepository.findById(id));
        }
    }

    @PostMapping("/user/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        //Check if existed user
        User existedUser = userService.findUserByEmail(user.getEmail());
        if(existedUser != null){
            log.info("Cannot create this account.\n\tEmail has existed!");
            return ResponseEntity.badRequest().body("Cannot create this account.\n\tEmail has existed!");
        }else{
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
            return ResponseEntity.created(uri).body(userService.saveUser(user));
        }
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public String addRoleToUser(@RequestBody RoleToUserModel toUserModel) throws Exception {
          return userService.addRoleToUser(toUserModel.getEmail(), toUserModel.getRoleName());
    }

    @GetMapping("/user/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);

               return ResponseEntity.ok().body(user);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("access_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            //throw new RuntimeException("Access token is missing !");
            return  ResponseEntity.ok().body(new RuntimeException("Access token is missing !"));
        }
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("access_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing !");
        }
    }

    @PostMapping("/user/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if(!userService.checkIfValidOldPassword(user, passwordModel.getOldPassword())) {
            return "Invalid Old Password";
        }
        //Save new password
        userService.changePassword(user, passwordModel.getNewPassword());
        return "Password changed successfully";
    }

    @PostMapping("/user/resetPassword")
    public String resetPassword(@RequestParam("clientLink") String clientLink,
                                @RequestBody PasswordModel passwordModel,
                                HttpServletRequest request,
                                MailRequest mailRequest)
            throws MessagingException
    {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if (user!= null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            //url = passwordResetTokenMail(user, applicationUrl(request), token);
            //Get Server Request by Client
            url = passwordResetTokenMail(user, applicationUrlClient(clientLink), token);
           /* mailService.sendMailWithoutAttachment(
                    "" + user.getEmail(),
                    "\tIf you have send Reset Password Request, please enter this link to reset your password: \n "+ url +
                            "\t( This link will be disabled after 10 minutes. )" +
                            "\n\n\tIf you are not the one who had send request to us, please check your Account's Security.",
                    "Hi "  + " from Maleficent System!"
            );*/
            mailRequest.setFrom("viethoang2001gun@gmail.com");
            mailRequest.setTo(user.getEmail());
            mailRequest.setSubject("Hello "+user.getEmail());
            mailRequest.setName(user.getUsername());

            Map<String, Object> model = new HashMap<>();
            model.put("resetPasswordLink", url);
           mailService.sendMailRequestedResetPassword(mailRequest,model);
        } else {
            return "User with entered email is not existed!";
        }
        return "Please check your Gmail to confirm.";
    }

    @PostMapping ("user/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel, MailRequest request) {
        String result = userService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")) {
            return "Invalid Token";
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()) {

            request.setFrom("viethoang2001gun@gmail.com");
            request.setTo(user.get().getEmail());
            request.setSubject("Hi there!");
            request.setName(user.get().getName());

            Map<String, Object> model = new HashMap<>();
            model.put("Name", request.getName());
            model.put("emailUser", request.getTo());
            model.put("location", "Hanoi, Vietnam");

            userService.changePassword(user.get(), passwordModel.getNewPassword());
            //mailService.sendMailWithTemplate(request, model);
            return mailService.sendMailWithTemplate(request, model) +  "\nPassword reset successfully";
        } else {
            return "Invalid Token";
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUserByAdmin(@RequestParam("userId") Long userId ,
                                             @RequestBody User user) {
        User userExisted = userRepository.findById(userId).get();
        if(userExisted != null){
            userExisted = userService.updateUserByID(userId, user);
            return ResponseEntity.ok(userExisted);
        }else{
            return ResponseEntity.badRequest().body("Cannot find User with Id " + userId);
        }
    }

    @PutMapping("/user/update-profile")
    public ResponseEntity<?> updateProfile(HttpServletRequest request,
                                           HttpServletResponse response,
                                           @RequestBody User userRequest) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);

                if(user != null){
                    user.setName(userRequest.getName());
                    user.setUsername(userRequest.getUsername());
                    user.setAvatar(userRequest.getAvatar());
                    user.setAddress(userRequest.getAddress());
                    user.setStatus(userRequest.getStatus());
                    user.setVirtualWallet(userRequest.getVirtualWallet());
                    userRepository.save(user);
                   return ResponseEntity.ok(user);
                }else{
                    return ResponseEntity.badRequest().body("Cannot find User with name " + username);
                }
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("access_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            return  ResponseEntity.ok().body(new RuntimeException("Access token is missing !"));
        }
        return ResponseEntity.ok().body("");
    }

    @PutMapping("/user/updateMoney")
    public ResponseEntity<?> updateMoneyUserByAdmin(@RequestParam("userId") Long userId ,
                                                    @RequestBody User user) {
        User userExisted = userRepository.findById(userId).get();
        if(userExisted != null){
            user.setVirtualWallet(userExisted.getVirtualWallet()+ user.getVirtualWallet());
            System.out.println(user);
            userExisted = userService.updateUserByID(userId, user);
            return ResponseEntity.ok(userExisted);
        }else{
            return ResponseEntity.badRequest().body("Cannot find User with Id " + userId);
        }
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
       /* String url =
                applicationUrl + "/savePassword?token="
                        + token;*/
        String url =
                applicationUrl + "/savePassword/"
                        + token;
        //sendVerificationEmail
        log.info("Click the link to Reset your password: {}",
                url);
        return url;
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + // http://locallhost:3000
                request.getServerName() +
                ":" +
                request.getServerPort() +
                "/api/user" +
                request.getContextPath();
    }

    private String applicationUrlClient(String request) {
        return request;
    }

    @GetMapping("/users/export-to-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Users_Information.xlsx";

        response.setHeader(headerKey, headerValue);
        userService.exportUserToExcel(response);
    }

}
