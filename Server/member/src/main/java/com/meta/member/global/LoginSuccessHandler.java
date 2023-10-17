package com.meta.member.global;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest requset, HttpServletResponse response, Authentication authentication) throws IOException {
//        response.sendRedirect("http://192.168.100.117:8011/api/member/oauth2/code/kakao");
//        response.sendRedirect("http://j9d201.p.ssafy.io:8011/api/member/oauth2/code/kakao");
//        response.sendRedirect("http://localhost:3000/auth");
//        response.sendRedirect("http://j9d201.p.ssafy.io:3000/meta/auth");
        response.sendRedirect("https://j9d201.p.ssafy.io/meta/auth");
    }
}