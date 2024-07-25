package com.example.healthcare.infra.config.security.jwt;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_TYPE = "Bearer ";
  private final JwtAuthenticationProvider jwtAuthenticationProvider;

  @Override
  protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response,
    FilterChain filterChain) throws ServletException, IOException {
    Optional.ofNullable(resolveToken(request))
      .filter(jwtAuthenticationProvider::validateToken)
      .map(jwtAuthenticationProvider::getAuthentication)
      .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
    filterChain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
      .filter(token -> StringUtils.hasText(token) && token.startsWith(BEARER_TYPE))
      .map(token -> token.substring(BEARER_TYPE.length()))
      .orElse(null);
  }
}