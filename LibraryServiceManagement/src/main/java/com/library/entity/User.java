package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @NonNull
    @Column(length = 100, name = "user_name")
    private String username;

    private String password;

    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String avatar;
    private String address;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private int virtualWallet;

    public enum AccountStatus{
        ACTIVE ,
        CLOSED,
        CANCELED,
        BLACKLISTED,
        NONE
    }

    public User(String name, @NonNull String username, String password,String phoneNumber, String email, String avatar, String address, AccountStatus status, int virtualWallet, Collection<Role> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber =phoneNumber;
        this.avatar = avatar;
        this.address = address;
        this.status = status;
        this.virtualWallet = virtualWallet;
        this.roles = roles;
    }

    /*
     * FetchType.EAGER: When you load User table from database, it will automatically load Role table as well
     * FetchType.LAZY : When you load User table from database, it just loads User from DB, you have to call getAllRoles()
     * from User to get their roles
     * */

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}