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
            new Role("ROLE_USER"),
            new Role("ROLE_MANAGER"),
            new Role("ROLE_ADMIN"),
            new Role("ROLE_SUPER_ADMIN")
    );

    @PostConstruct
    public void saveRole() {
        roleRepository.saveAll(roles);
    }
}

