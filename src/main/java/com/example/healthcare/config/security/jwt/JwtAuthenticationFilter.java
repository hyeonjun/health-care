package com.example.healthcare.config.security.jwt;

import com.example.healthcare.account.domain.code.AuthorityType;
import com.example.healthcare.account.service.dto.LoginDTO;
import com.example.healthcare.config.security.user.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.concurrent.TimeUnit;


import java.io.IOException;


@Slf4j(topic = "로그인 및 JWT 생성")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final RedisTemplate redisTemplate;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("request uri: {}", request.getRequestURI());

        try {
            LoginDTO dto = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    dto.email(),
                    dto.password()
            );
            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        String email = ((LoginUser) authResult.getPrincipal()).getEmail();
        AuthorityType authorityType = ((LoginUser) authResult.getPrincipal()).getAuthorityType();
        Long userId = ((LoginUser) authResult.getPrincipal()).getId();

        String accessToken = jwtUtil.createAccessToken(email, authorityType);
        response.addHeader(JwtUtil.ACCESS_TOKEN, accessToken);

        String refreshToken = jwtUtil.createRefreshToken(email);
        response.addHeader(JwtUtil.REFRESH_TOKEN, refreshToken);

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(userId.toString(), refreshToken, 30, TimeUnit.DAYS);

        response.setStatus(200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(200);
        new ObjectMapper().writeValue(response.getOutputStream(), ("아이디와 비밀번호를 한번 더 확인해 주세요"));
    }
}
