package com.team.youarelikemetoo.Global.JWT;

import com.team.youarelikemetoo.Auth.DTO.CustomOAuth2User;
import com.team.youarelikemetoo.Global.JWT.Service.RedisService;
import com.team.youarelikemetoo.User.DTO.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RedisService redisService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // /login 요청은 JWT 검증하지 않음
        if (path.equals("/login")) {
            log.info("Skip JWT filter for /login");
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization 헤더에서 Bearer 토큰 추출
        String authHeader = request.getHeader("Authorization");


        // 헤더가 없거나 Bearer 형식이 아니면 필터 통과
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("No Bearer token found in header");
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " 접두어 제거하고 순수 토큰만 추출
        String token = authHeader.substring(7);

        //토큰 유효기간 검증
        if(jwtUtil.isExpired(token)) {
            log.info("token is expired");

            // 이거 하면 값을 반환해야하는거 아닌가??
            filterChain.doFilter(request,response);

            return;
        }

        // 블랙리스트 검증
        if(redisService.isBlacklisted(token)){
            filterChain.doFilter(request,response);
            return;
        }

        // 토큰에서 oauthId, role 획득
        String oauthId = jwtUtil.getOauthId(token);
        String role = jwtUtil.getRole(token);

        // UserDTO를 생성하여 값 set
        UserDTO userDTO = UserDTO.builder()
                .oauthId(oauthId)
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
