package com.example.blog2.service;

import com.example.blog2.dto.categoryDTO;
import com.example.blog2.dto.postDTO;
import com.example.blog2.entity.category;
import com.example.blog2.entity.post;
import com.example.blog2.repository.categoryRepository;
import com.example.blog2.repository.postRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class categoryServiceImpl implements categoryService{
    private final categoryRepository CategoryRepository;
    @Transactional
    @Override
    public String updateCategory(category Category) {
        LocalDateTime today = LocalDateTime.now();
        Category.setModifiedDate(today);
        CategoryRepository.save(Category);
        return Category.getId();
    }
    @Transactional
    @Override
    public String saveCategory(categoryDTO CategoryDTO) {
        LocalDateTime today = LocalDateTime.now();
        category Category = dtoToEntity(CategoryDTO);
        Category.setCreatedDate(today);
        CategoryRepository.save(Category);
        return Category.getId();
    }
    @Override
    public List<category> categories(String name){
        return CategoryRepository.findAllByUser(name);
    }

    @Override
    public Long deleteCategoryByName(String name){
        Long deleted = CategoryRepository.deleteCategoryByName(name);
        return deleted;
    }
}
