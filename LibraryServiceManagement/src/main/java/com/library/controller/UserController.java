package com.library.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entity.Role;
import com.library.entity.User;
import com.library.entity.email.MailRequest;
import com.library.model.PasswordModel;
import com.library.model.RoleToUserModel;
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

@EnableScheduling
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Autowired
    private MailService mailService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
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
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request)
            throws MessagingException
    {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if (user!= null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);
            mailService.sendMailWithAttachment(
                    "" + user.getEmail(),
                    "\tIf you have send Reset Password Request, please enter this link to reset your password: \n "+ url +
                            "\t( This link will be disabled after 10 minutes. )" +
                            "\n\n\tIf you are not the one who had send request to us, please check your Account's Security.",
                    "Hi "  + " from Maleficent System!",
                    "E:\\epc\\Meme pics\\Anya Meme.jpg"
            );
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
            model.put("location", "Hanoi, Vietnam");

            userService.changePassword(user.get(), passwordModel.getNewPassword());
            //mailService.sendMailWithTemplate(request, model);
            return mailService.sendMailWithTemplate(request, model) +  "\nPassword reset successfully";
        } else {
            return "Invalid Token";
        }
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url =
                applicationUrl + "/savePassword?token="
                        + token;
        //sendVerificationEmail
        log.info("Click the link to Reset your password: {}",
                url);
        return url;
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                "/api/user" +
                request.getContextPath();
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
