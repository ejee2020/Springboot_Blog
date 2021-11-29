package com.example.blog2.service;

import com.example.blog2.dto.userAuthDTO;
import com.example.blog2.entity.user;
import com.example.blog2.repository.userRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {
    private final userRepository UserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<user> result = UserRepository.findById(username);
        if (!result.isPresent()){
            throw new UsernameNotFoundException("Check email");
        }
        user User = result.get();
        userAuthDTO userAuth = new userAuthDTO(
                User.getId(),
                User.getPassword(),
                User.getName(),
                User.getEmail(),
                User.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()));
        return userAuth;
    }
}
