package com.library.service;

import com.library.entity.Role;
import com.library.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    String addRoleToUser(String email, String roleName);

    User getUser(String username);

    User findUserByEmail(String email);

    List<User> getUsers();
    User updateUserByID(Long id, User user);
    User updateUserByLoggedIn( User user);

    //Password Reset
    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    //Change password
    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);

    //Export data
    List<User> exportUserToExcel(HttpServletResponse response) throws IOException;
}
