package com.example.blog2.service;
import com.example.blog2.dto.userDTO;
import com.example.blog2.entity.post;
import com.example.blog2.entity.user;
import com.example.blog2.entity.userRole;
import com.example.blog2.repository.userRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class userServiceImpl implements userService{
    private final userRepository UserRepository;
    @Override
    public boolean matchPassword(String raw, String encoded){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(raw, encoded);
    }
    @Transactional
    @Override
    public String signUp(userDTO UserDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserDTO.setPassword(passwordEncoder.encode(UserDTO.getPassword()));
        user User = dtoToEntity(UserDTO);
        User.addUserRole(userRole.USER);
        UserRepository.save(User);
        return User.getId();
    }

    @Override
    public Optional<user> getUser(String Id) {
        return UserRepository.findById(Id);
    }
    @Override
    public boolean duplicate_id(String id){
        Optional<user> User = getUser(id);
        return User.isPresent();
    }
    @Override
    public user find_by_name_and_email(userDTO UserDTO){
        String name = UserDTO.getName();
        String email = UserDTO.getEmail();
        Optional<user> User = UserRepository.findByName(name);
        if (User.isPresent()) {
            if (User.get().getEmail().equals(email)){
                return User.get();
            }
            return null;
        }
        return null;
    }
    @Override
    public user find_by_name_email_and_id(userDTO UserDTO) {
        String name = UserDTO.getName();
        String email = UserDTO.getEmail();
        String id = UserDTO.getId();
        List<user> users = UserRepository.findAllByName(name);
        if (users.size() == 1){
            user candidate = users.get(0);
            if (candidate.getId() == id){
                if (candidate.getEmail() == email){
                    return candidate;
                }
            }
            return null;
        }
        for (user User : users){
            if (User.getEmail().equals(email)){
                if (User.getId().equals(id)){
                    return User;
                }
            }
        }
        return null;
    }

    @Override
    public Optional<user> find_by_id(String id){
        Optional<user> User = UserRepository.findById(id);
        return User;
    }

    @Transactional
    @Override
    public String updateUser(user User) {
        LocalDateTime today = LocalDateTime.now();
        return User.getId();
    }
}

