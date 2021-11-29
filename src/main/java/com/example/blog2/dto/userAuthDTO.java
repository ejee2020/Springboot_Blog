package com.example.blog2.dto;

import com.example.blog2.entity.user;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
public class userAuthDTO extends User {
    private String id;
    private String email;
    private String name;
    public userAuthDTO(
            String username, String password, String name, String email, Collection<? extends GrantedAuthority> authorities){
                super(username, password, authorities);
                this.id = username;
                this.name = name;
                this.email = email;
    }
}
