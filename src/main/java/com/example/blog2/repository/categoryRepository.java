package com.example.blog2.repository;

import com.example.blog2.entity.category;
import com.example.blog2.entity.post;
import com.example.blog2.entity.user;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface categoryRepository extends MongoRepository<category, String> {
    List<category> findAllByUser(String user);
    Long deleteCategoryByName(String name);
}
