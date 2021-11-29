package com.example.blog2.service;

import com.example.blog2.dto.postDTO;
import com.example.blog2.entity.post;
import com.example.blog2.entity.user;
import com.example.blog2.entity.userRole;
import com.example.blog2.repository.postRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class postServiceImpl implements postService{
    @Autowired
    private final postRepository PostRepository;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Transactional
    @Override
    @Cacheable(value = "posts")
    public post savePost(postDTO PostDTO) {
        LocalDateTime today = LocalDateTime.now();
        post Post = dtoToEntity(PostDTO);
        Post.setCreatedDate(today);
        PostRepository.save(Post);
        return Post;
    }

    @Transactional
    @Override
    @CachePut(value = "posts")
    public post updatePost(post Post) {
        LocalDateTime today = LocalDateTime.now();
        Post.setModifiedDate(today);
        PostRepository.save(Post);
        return Post;
    }

    @Override
    @Cacheable(value = "posts", key = "#writer")
    public List<post> posts(String writer){
        List<post> posts = PostRepository.findAllByWriter(writer);
        return posts;
    }

    @Override
    public List<post> postsByCategory(String writer, String category){
        List<post> posts = PostRepository.findAllByCategoryAndWriter(category, writer);
        return posts;
    }

    @CacheEvict(value= "posts", allEntries = true)
    @Override
    public Long deletePostById(String id){
        Long deleted = PostRepository.deletePostById(id);
        return deleted;
    }

    @Override
    public void deletePostsByCategory(String category){
        if (PostRepository.existsPostByCategory(category)) {
            PostRepository.deletePostsByCategory(category);
        }
    }
    @Override
    public void updatePostsByCategory(String before, String category){
        List<post> posts = PostRepository.findAllByCategory(before);
        LocalDateTime today = LocalDateTime.now();
        for (post Post: posts){
            Post.setCategory(category);
            Post.setModifiedDate(today);
            PostRepository.save(Post);
        }
    }
}
