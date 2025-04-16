package com.team.youarelikemetoo.global.jwt;

import com.team.youarelikemetoo.auth.dto.CustomOAuth2User;
import com.team.youarelikemetoo.global.config.SecurityConfig;
import com.team.youarelikemetoo.global.jwt.service.RedisService;
import com.team.youarelikemetoo.user.dto.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    private boolean isExcluded(String path) {
        return Arrays.stream(SecurityConfig.EXCLUDE_PATHS)
                .anyMatch(pattern -> {
                    if (pattern.endsWith("/**")) {
                        String prefix = pattern.substring(0, pattern.length() - 3);
                        return path.startsWith(prefix);
                    }
                    return path.equals(pattern);
                });
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        log.info("path : {} ",path);

        // /login 요청은 JWT 검증하지 않음
        if (isExcluded(path)) {
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
        String token = authHeader.substring(7).trim();


        //토큰 유효기간 검증
        if(jwtUtil.isExpired(token)) {
            log.info("AccessToken is expired: {}");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"Access token is expired\"}");
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
