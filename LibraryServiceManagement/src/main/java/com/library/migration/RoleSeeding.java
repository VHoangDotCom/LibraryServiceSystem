package com.library.migration;

import com.library.entity.Role;
import com.library.repository.RoleRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class RoleSeeding {

    private RoleRepository roleRepository;

    public RoleSeeding(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private List<Role> roles = Arrays.asList(
            new Role("USER"),
            new Role("MEMBER"),
            new Role("LIBRARIAN"),
            new Role("ADMIN")
    );

    @PostConstruct
    public void saveRole() {
        List<Role> listRoleExisted = roleRepository.findAll();
        if(listRoleExisted == null){
            roleRepository.saveAll(roles);
        }
    }
}

