package com.example.blog2.service;

import com.example.blog2.dto.categoryDTO;
import com.example.blog2.dto.postDTO;
import com.example.blog2.entity.category;
import com.example.blog2.entity.post;

import java.util.List;

public interface categoryService {
    public List<category> categories(String name);
    public String saveCategory(categoryDTO CategoryDTO);
    public Long deleteCategoryByName(String name);
    public String updateCategory(category Category);
    default category dtoToEntity(categoryDTO CategoryDTO){
        category Category = category.builder()
                .user(CategoryDTO.getUser())
                .name(CategoryDTO.getName())
                .createdDate(CategoryDTO.getCreatedDate())
                .modifiedDate(CategoryDTO.getModifiedDate())
                .build();
        return Category;
    }
    default categoryDTO entityToDTO(category Category){
        categoryDTO CategoryDTO = categoryDTO.builder()
                .name(Category.getName())
                .user(Category.getUser())
                .createdDate(Category.getCreatedDate())
                .modifiedDate(Category.getModifiedDate())
                .build();
        return CategoryDTO;
    }
}
