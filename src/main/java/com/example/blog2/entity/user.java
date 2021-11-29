package com.example.blog2.entity;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;


@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class user extends baseEntity{
    @Indexed
    private String id;
    private String password;
    private String email;
    private String name;
    @Builder.Default
    private Set<userRole> roleSet = new HashSet<>();

    public void addUserRole(userRole UserRole){
        roleSet.add(UserRole);
    }
    public user(String id, String password, String email, String name){
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.roleSet = new HashSet<>();
    }
}