package com.example.blog2.config;

import com.example.blog2.controller.LoginSuccessHandler;
import com.example.blog2.service.userService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@Log4j2
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private userService UserService;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/index").permitAll()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/user/find_id").permitAll()
                .antMatchers("/user/find_password").permitAll()
                .antMatchers("/user/main_page").hasRole("USER");
        http.formLogin()
                        .loginPage("/user/index")
                                .usernameParameter("id")
                                        .passwordParameter("password")
        .loginProcessingUrl("/user/main_page")
                .successHandler(new LoginSuccessHandler());
        http.csrf().disable();
        http.logout()
                .logoutSuccessUrl("/user/index");
    }
}
