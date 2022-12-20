package com.library;

import com.library.entity.User;
import com.library.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class LibraryServiceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceManagementApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(UserService userService) {
        return args -> {

            userService.saveUser(new User(null, "John Deep", "john", "1234","john@gmail.com","1.png","Hanoi",User.AccountStatus.ACTIVE, new ArrayList<>()));
            userService.saveUser(new User(null, "Will Smith", "will", "1234","will@gmaol.com","2.png","Hanoi",User.AccountStatus.BLACKLISTED, new ArrayList<>()));
            userService.saveUser(new User(null, "Jim Carry", "jim", "1234","hoangnvth2010033@fpt.edu.vn","3.png","Hanoi",User.AccountStatus.CANCELED, new ArrayList<>()));
            userService.saveUser(new User(null, "Viet Hoang", "hoang", "hoang","viethoang2001gun@gmail.com","4.png","Hanoi",User.AccountStatus.CLOSED, new ArrayList<>()));

            //userService.addRoleToUser("john@gmail.com", "ROLE_USER");
            userService.addRoleToUser("will@gmaol.com", "MEMBER");
            userService.addRoleToUser("hoangnvth2010033@fpt.edu.vn", "LIBRARIAN");
            userService.addRoleToUser("viethoang2001gun@gmail.com", "ADMIN");
            //userService.addRoleToUser("viethoang2001gun@gmail.com", "ROLE_USER");
            userService.addRoleToUser("viethoang2001gun@gmail.com", "ADMIN");
            userService.addRoleToUser("will@gmaol.com", "LIBRARIAN");
        };
    }
}
