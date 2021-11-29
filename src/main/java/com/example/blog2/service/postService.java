package com.example.blog2.service;

import com.example.blog2.dto.postDTO;
import com.example.blog2.entity.post;

import java.util.List;

public interface postService {
    public post savePost(postDTO PostDTO);
    public List<post> posts(String writer);
    public List<post> postsByCategory(String writer, String category);
    public post updatePost(post Post);
    public Long deletePostById(String id);
    public void deletePostsByCategory(String category);
    public void updatePostsByCategory(String before, String category);
    default post dtoToEntity(postDTO PostDTO){
        post Post = post.builder()
                .writer(PostDTO.getWriter())
                .title(PostDTO.getTitle())
                .post_text(PostDTO.getPost_text())
                .category(PostDTO.getCategory())
                .build();
        return Post;
    }
    default postDTO entityToDTO(post Post){
        postDTO PostDTO = postDTO.builder()
                .id(Post.getId())
                .title(Post.getTitle())
                .post_text(Post.getPost_text())
                .category(Post.getCategory())
                .build();
        return PostDTO;
    }
}

