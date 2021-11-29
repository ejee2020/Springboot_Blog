package com.example.blog2.controller;

import com.example.blog2.dto.categoryDTO;
import com.example.blog2.dto.userAuthDTO;
import com.example.blog2.dto.userDTO;
import com.example.blog2.entity.category;
import com.example.blog2.entity.post;
import com.example.blog2.entity.user;
import com.example.blog2.service.categoryService;
import com.example.blog2.service.postService;
import com.example.blog2.service.userService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/*")
@Log4j2
@RequiredArgsConstructor
public class userController {
    private final userService UserService;
    private final postService PostService;
    private final categoryService CategoryService;
    @GetMapping("/find_password")
    public String findPasswordForm(Model model){
        model.addAttribute("UserDTO", new userDTO());
        return "/user/find_password_form";
    }
    @GetMapping("/find_password_result")
    public String findPasswordResultGet(Model model){
        model.addAttribute("UserDTO", new userDTO());
        return "/user/find_password_form";
    }
    @PostMapping("/find_password_result")
    public String findPasswordResult(Model model, @ModelAttribute userDTO UserDTO, HttpServletResponse response) throws Exception{
        user searched = UserService.find_by_name_email_and_id(UserDTO);
        if (searched != null){
            model.addAttribute("User", searched);
            return "/user/find_password_result";
        } else {
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('정보에 해당되는 아이디가 없습니다!'); </script>");
            out.flush();
            model.addAttribute("UserDTO", new userDTO());
            return "/user/find_password_form";
        }
    }
    @GetMapping("/find_id")
    public String findIdForm(Model model){
        model.addAttribute("UserDTO", new userDTO());
        return "/user/find_id_form";
    }
    @GetMapping("/find_id_result")
    public String find_id_result_get(Model model){
        model.addAttribute("UserDTO", new userDTO());
        return "/user/find_id_form";
    }
    @PostMapping("/find_id_result")
    public String find_id_result(Model model, @ModelAttribute userDTO UserDTO, HttpServletResponse response) throws Exception{
        user searched = UserService.find_by_name_and_email(UserDTO);
        if (searched != null){
            model.addAttribute("User", searched);
            return "/user/find_id_result";
        } else {
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('정보에 해당되는 아이디가 없습니다!'); </script>");
            out.flush();
            model.addAttribute("UserDTO", new userDTO());
            return "/user/find_id_form";
        }
    }
    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("UserDTO", new userDTO());
        return "/user/sign_up_form";
    }
    @PostMapping("/sign_up_result")
    public String signup(Model model, userDTO UserDTO, HttpServletResponse response) throws Exception {
        if (UserService.duplicate_id(UserDTO.getId())) {
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('이미 사용중인 아이디 입니다!'); </script>");
            out.flush();
            model.addAttribute("UserDTO", new userDTO());
            return "/user/sign_up_form";
        } else {
            UserService.signUp(UserDTO);
            model.addAttribute("UserDTO", new userDTO());
            return "user/index";
        }
    }
    @GetMapping("/index")
    public String login_get(Model model){
        model.addAttribute("UserDTO", new userDTO());
        return "/user/index";
    }
    @GetMapping("/main_page")
    public String main_page_get(@AuthenticationPrincipal userAuthDTO userAuth, Model model){
        model.addAttribute("UserAuth", userAuth);
        String writer = userAuth.getId();
        List<post> posts = PostService.posts(writer);
        List<category> categories = CategoryService.categories(writer);
        model.addAttribute("Posts", posts);
        model.addAttribute("Categories", categories);
        return "/user/main_page";
    }
    @PostMapping("/main_page")
    public String main_page_get2(@AuthenticationPrincipal userAuthDTO userAuth, Model model){
        model.addAttribute("UserAuth", userAuth);
        return "/user/main_page2";
    }

    @PostMapping("/main")
    public String main_page_post(Model model, @ModelAttribute userDTO UserDTO, HttpServletResponse response)
            throws Exception{
        user User = UserService.dtoToEntity(UserDTO);
        Optional<user> searched = UserService.getUser(User.getId());
        if (searched.isPresent()) {
            if (UserService.matchPassword(User.getPassword(), searched.get().getPassword())){
                return "redirect:/user/main_page";
            } else {
                response.setContentType("text/html; charset=euc-kr");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('아이디 혹은 비밀번호를 확인하세요!'); </script>");
                out.flush();
                model.addAttribute("UserDTO", new userDTO());
                return "/user/index";
            }
        }
        else {
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('등록되지 않은 아이디 입니다!'); </script>");
            out.flush();
            model.addAttribute("UserDTO", new userDTO());
            return "/user/index";
        }
    }
    @GetMapping("/modify_categories")
    public String modify_categories(Model model, Principal principal){
        String name = principal.getName();
        List<category> categories = CategoryService.categories(name);
        model.addAttribute("categories", categories);
        return "/user/modify_categories";
    }

    @GetMapping("/add_new_category")
    public String add_new_category(Model model){
        model.addAttribute("categoryDTO", new categoryDTO());
        return "/user/add_new_category_form";
    }
    @PostMapping("/add_new_category")
    public String add_new_category(@AuthenticationPrincipal userAuthDTO userAuth,Principal principal, @ModelAttribute categoryDTO CategoryDTO, Model model){
        String writer = userAuth.getId();
        String name = principal.getName();
        CategoryDTO.setUser(name);
        CategoryService.saveCategory(CategoryDTO);
        List<category> categories = CategoryService.categories(writer);
        model.addAttribute("categories", categories);
        return "/category/modify_categories";
    }

    @PostMapping("/view_by_categories")
    public String view_by_category(Principal principal, @ModelAttribute category Category, Model model){
        String writer = principal.getName();
        List<post> posts = PostService.postsByCategory(writer, Category.getName());
        model.addAttribute("posts", posts);
        return "/user/view_by_categories";
    }
    @GetMapping("/modify_user_info")
    public String modify_user_info(Principal principal, @ModelAttribute user User, Model model){

        return "/user/modify_user_info";
    }

    @PostMapping("/modify_user_info")
    public String update_user_info(Principal principal, @ModelAttribute user User, Model model){

        return "/user/modify_user_info";
    }


}
