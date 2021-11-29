package com.example.blog2.repository;

import com.example.blog2.entity.post;
import com.example.blog2.entity.user;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface postRepository extends MongoRepository<post, String> {
    Optional<post> findById(String id);
    List<post> findAllByWriter(String writer);
    List<post> findAllByCategoryAndWriter(String category, String writer);
    List<post> findAllByCategory(String category);
    Long deletePostById(String id);
    Long deletePostsByCategory(String category);
    boolean existsPostByCategory(String category);
}
