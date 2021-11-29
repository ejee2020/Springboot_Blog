package com.example.blog2.controller;

import com.example.blog2.dto.categoryDTO;
import com.example.blog2.dto.postDTO;
import com.example.blog2.dto.userAuthDTO;
import com.example.blog2.dto.userDTO;
import com.example.blog2.entity.category;
import com.example.blog2.entity.post;
import com.example.blog2.service.categoryService;
import com.example.blog2.service.postService;
import com.example.blog2.service.userService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/category/*")
@Log4j2
@RequiredArgsConstructor
public class categoryController {
    private final userService UserService;
    private final postService PostService;
    private final categoryService CategoryService;
    @GetMapping("/modify_categories")
    public String modify_categories(Model model, Principal principal){
        String name = principal.getName();
        List<category> categories = CategoryService.categories(name);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryDTO", new categoryDTO());
        return "/category/modify_categories";
    }

    @PostMapping("/delete_category")
    public String delete_category(Principal principal, Model model, @ModelAttribute category Category){
        PostService.deletePostsByCategory(Category.getName());
        CategoryService.deleteCategoryByName(Category.getName());
        String name = principal.getName();
        List<category> categories = CategoryService.categories(name);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryDTO", new categoryDTO());
        return "/category/modify_categories";
    }

    @PostMapping("/move_to_modify_category")
    public String move_to_modify_category(Principal principal,Model model, @ModelAttribute category Category){
        System.out.println("DEBUG");
        System.out.println(Category.getName());
        model.addAttribute("category", Category);
        return "/category/modify_category";
    }
    @PostMapping("/modify_category")
    public String modify_category(@AuthenticationPrincipal userAuthDTO userAuth,Model model, @ModelAttribute category Category, @RequestParam(value = "test") String test){
        System.out.println("DEBUG");
        System.out.println(Category.getName());
        System.out.println(test);
        String writer = userAuth.getId();
        model.addAttribute("category", Category);
        Category.setUser(writer);
        PostService.updatePostsByCategory(test, Category.getName());
        CategoryService.updateCategory(Category);
        List<post> posts = PostService.posts(writer);
        List<category> categories = CategoryService.categories(writer);
        model.addAttribute("Categories", categories);
        model.addAttribute("Posts", posts);
        model.addAttribute("UserAuth", userAuth);
        return "/user/main_page";
    }

}
