package com.library;

import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

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
    CommandLineRunner runner(UserService userService, UserRepository userRepository) {
        return args -> {

            List<User> listUserExisted = userRepository.findAll();
            if(listUserExisted == null){
                userService.saveUser(new User( "John Deep", "john", "1234","053453455","john@gmail.com","1.png","Hanoi",User.AccountStatus.ACTIVE,500000, new ArrayList<>()));
                userService.saveUser(new User("Will Smith", "will", "1234","053453455","will@gmaol.com","2.png","Hanoi",User.AccountStatus.BLACKLISTED,50000, new ArrayList<>()));
                userService.saveUser(new User( "Jim Carry", "jim", "1234","053453455","hoangnvth2010033@fpt.edu.vn","3.png","Hanoi",User.AccountStatus.CANCELED,50000, new ArrayList<>()));
                userService.saveUser(new User( "Viet Hoang", "hoang", "hoang","053453455","viethoang2001gun@gmail.com","4.png","Hanoi",User.AccountStatus.CLOSED,50000, new ArrayList<>()));

                //userService.addRoleToUser("john@gmail.com", "ROLE_USER");
                userService.addRoleToUser("will@gmaol.com", "USER");
                userService.addRoleToUser("hoangnvth2010033@fpt.edu.vn", "USER");
                userService.addRoleToUser("viethoang2001gun@gmail.com", "USER");
                //ADMIN  LIBRARIAN  MEMBER  USER
                userService.addRoleToUser("viethoang2001gun@gmail.com", "ADMIN");
                userService.addRoleToUser("john@gmail.com", "USER");
            }
        };
    }
}
