package com.example.blog2.repository;


import com.example.blog2.entity.user;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface userRepository extends MongoRepository<user, String> {
    Optional<user> findByName(String name);
    List<user> findAllByName(String name);
}
