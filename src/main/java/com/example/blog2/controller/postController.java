package com.example.blog2.controller;

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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/post/*")
@Log4j2
@RequiredArgsConstructor
public class postController {
    private final postService PostService;
    private final categoryService CategoryService;
    @GetMapping("new_post")
    public String write_new_post(Model model, Principal principal){
        String name = principal.getName();
        List<category> categories = CategoryService.categories(name);
        model.addAttribute("PostDTO", new postDTO());
        model.addAttribute("categories", categories);
        return "/post/new_post";
    }
    @PostMapping("save_post")
    public String save_new_post(@AuthenticationPrincipal userAuthDTO userAuth, Principal principal, Model model, @ModelAttribute postDTO PostDTO){
        String name = principal.getName();
        PostDTO.setWriter(name);
        PostService.savePost(PostDTO);
        String writer = userAuth.getId();
        List<post> posts = PostService.posts(writer);
        List<category> categories = CategoryService.categories(writer);
        model.addAttribute("Categories", categories);
        model.addAttribute("Posts", posts);
        model.addAttribute("UserAuth", userAuth);
        return "/user/main_page";
    }
    @PostMapping(value = "modify_post")
    public String modify_post(Principal principal, @ModelAttribute post post, Model model) throws Exception{
        model.addAttribute("post", post);
        String name = principal.getName();
        List<category> categories = CategoryService.categories(name);
        model.addAttribute("categories", categories);
        return "/post/modify_post_form";
    }
    @PostMapping("update_post")
    public String update_post(@AuthenticationPrincipal userAuthDTO userAuth, Principal principal, Model model, @ModelAttribute post Post){
        String name = principal.getName();
        Post.setWriter(name);
        PostService.updatePost(Post);
        String writer = userAuth.getId();
        List<post> posts = PostService.posts(writer);
        List<category> categories = CategoryService.categories(writer);
        model.addAttribute("Categories", categories);
        model.addAttribute("Posts", posts);
        model.addAttribute("UserAuth", userAuth);
        return "/user/main_page";
    }
    @PostMapping("delete_post")
    public String delete_post(@AuthenticationPrincipal userAuthDTO userAuth, Model model, @ModelAttribute post Post){
        PostService.deletePostById(Post.getId());
        String writer = userAuth.getId();
        List<post> posts = PostService.posts(writer);
        List<category> categories = CategoryService.categories(writer);
        model.addAttribute("Categories", categories);
        model.addAttribute("Posts", posts);
        model.addAttribute("UserAuth", userAuth);
        return "/user/main_page";
    }
}
