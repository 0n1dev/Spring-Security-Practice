package com.example.springsecuritypractice;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated();

        http.formLogin() // Form 로그인 인증 기능 작동
                // .loginPage("/loginPage") // 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/") // 로그인 성공 후 이동 페이지
                .failureUrl("/login") // 로그인 실패 후 이동 페이지
                .usernameParameter("userId") // 아이디 파라메터명 설정
                .passwordParameter("passwd") // 패스워드 파라메터명 설정
                .loginProcessingUrl("/login_proc") // 로그인 Form Action Url
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        System.out.println("authentication : " + authentication.getName());
                        response.sendRedirect("/");
                    }
                }) // 로그인 성공 핸들러
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println("exception : " + exception.getMessage());
                        response.sendRedirect("/login");
                    }
                }) // 로그인 실패 핸들러
                .permitAll(); // 누구드닞 접근 가능하도록 권한 설정.
    }
}