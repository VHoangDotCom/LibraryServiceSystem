package com.library.service.impl;

import com.library.entity.Category;
import com.library.entity.Role;
import com.library.entity.User;
import com.library.entity.token.PasswordResetToken;
import com.library.repository.PasswordResetTokenRepository;
import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import com.library.service.export_excel.ExcelExportUsers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        //Account auto has role 'Member' whenever created
        Role role = roleRepository.findByName("MEMBER");
        Role role1 = roleRepository.findByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        roles.add(role1);
        user.setRoles(roles);
        user.setVirtualWallet(50000);
        if(user.getAvatar() == null){
            user.setAvatar("https://cdn2.vectorstock.com/i/1000x1000/23/81/default-avatar-profile-icon-vector-18942381.jpg");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new user {} to the database", user.getName());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public String addRoleToUser(String email, String roleName) {
        User user = userRepo.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        if(user == null) {
            return "Cannot find User with email: " +email+" !";
        }if(role == null){
            return "Role "+roleName+" is not existed !";
        }if(user != null && role != null){
            if( user.getRoles().contains(role)){
                return "User " + email + " has already have role " + roleName + " !";
            }
            user.getRoles().add(role);
            log.info("Adding role " + roleName + " to " + email + " successfully!");
            return "Adding role " + roleName + " to " + email + " successfully!";
        }
        return "";
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken
                = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public User updateUserByID(Long id, User user) {
        User userExisted = userRepo.findById(id).get();
        userExisted.setName(user.getName());
        userExisted.setUsername(user.getUsername());
        userExisted.setAvatar(user.getAvatar());
        userExisted.setAddress(user.getAddress());
        userExisted.setPhoneNumber(user.getPhoneNumber());
        userExisted.setStatus(user.getStatus());
        userExisted.setVirtualWallet(user.getVirtualWallet());
        userExisted.setEmail(user.getEmail());

        userRepo.save(userExisted);
        return userExisted;
    }

    @Override
    public User updateUserByLoggedIn( User user) {
        user.setName(user.getName());
        user.setUsername(user.getUsername());
        user.setAvatar(user.getAvatar());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setAddress(user.getAddress());
        user.setStatus(user.getStatus());
        user.setVirtualWallet(user.getVirtualWallet());
        user.setEmail(user.getEmail());

        userRepo.save(user);
        return user;
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken
                = passwordResetTokenRepository.findByToken(token);

        if(passwordResetToken == null) {
            return "invalid";
        }

        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();

        if((passwordResetToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "token has expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public List<User> exportUserToExcel(HttpServletResponse response) throws IOException {
        List<User> users = userRepo.findAll();
        ExcelExportUsers exportUtils = new ExcelExportUsers(users);
        exportUtils.exportUserDataToExcel(response);
        return users;
    }

}
