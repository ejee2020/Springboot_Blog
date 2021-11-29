package com.example.blog2;

import com.example.blog2.entity.user;
import com.example.blog2.entity.userRole;
import com.example.blog2.repository.userRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
public class UserTests {
    @Autowired
    private userRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            user User = user.builder()
                    .password(passwordEncoder.encode("1111"))
                    .id("id" + i)
                    .email("user" + i + "@zerock.org")
                    .name("사용자" + i)
                    .build();
            User.addUserRole(userRole.USER);
            repository.save(User);
        });

    }

}
