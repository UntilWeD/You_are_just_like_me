package com.team.youarelikemetoo.Global.JWT;

import com.team.youarelikemetoo.Auth.DTO.CustomOAuth2User;
import com.team.youarelikemetoo.User.DTO.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        for ( Cookie cookie : cookies ){

            log.info(cookie.getName());
            if(cookie.equals("Authorization")){
                authorization = cookie.getValue();
            }
        }

        //Authorization 헤더 검증
        if(authorization == null){

            log.info("token is null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료(필수)
            return;
        }

        //토큰
        String token = authorization;

        //토큰 유효기간 검증
        if(jwtUtil.isExpired(token)) {
            log.info("token is expired");
            filterChain.doFilter(request,response);

            return;
        }

        // 토큰에서 username, role 획득
        String name = jwtUtil.getName(token);
        String role = jwtUtil.getRole(token);

        // UserDTO를 생성하여 값 set
        UserDTO userDTO = UserDTO.builder()
                .name(name)
                .role(role)
                .build();

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
