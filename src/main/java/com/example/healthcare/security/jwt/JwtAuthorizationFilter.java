package com.example.healthcare.security.jwt;

import com.example.healthcare.security.user.UserDetailsImpl;
import com.example.healthcare.security.user.UserDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.healthcare.security.jwt.JwtUtil.*;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = getJwtFromHeader(req);
        if (StringUtils.hasText(tokenValue)) {
            if (!jwtUtil.validateToken(tokenValue)) {
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
            log.info("info:{}", info.getSubject());

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new AuthorizationServiceException(e.getMessage());

            }
        }

        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String email) {
        Authentication authentication = createAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("인증처리: {}", (SecurityContextHolder.getContext().getAuthentication()));
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // header 에서 TOKEN 추출 후, AccessToken 을 가져오는 메서드
    private String getJwtFromHeader(HttpServletRequest request) throws JsonProcessingException {
        String token = request.getHeader(TOKEN);
        String accessToken = null;
        if (StringUtils.hasText(token)) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode tokenNode = objectMapper.readTree(token);
                accessToken = tokenNode.get("accessToken").asText();
        }

        if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)) {
            return accessToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

}