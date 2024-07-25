package com.example.healthcare.config.security.jwt;

import com.example.healthcare.account.domain.User;
import com.example.healthcare.auth.repository.AccessTokenBlackListRepository;
import com.example.healthcare.common.exception.AuthException;
import com.example.healthcare.common.exception.AuthException.AuthExceptionCode;
import com.example.healthcare.config.security.user.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

  private final JwtProperties jwtProperties;
  private final AccessTokenBlackListRepository accessTokenBlackListRepository;

  public Authentication getAuthentication(String accessToken) {
    Claims claims = getParseClaims(accessToken);
    validateToken(accessToken);
    return new UsernamePasswordAuthenticationToken(
      new LoginUser(new User(claims)), "", getAuthorities(claims));
  }

  public boolean validateToken(String token) {
    try {
      getParseClaims(token);
      if (accessTokenBlackListRepository.existsById(token)) {
        throw new AuthException(AuthExceptionCode.ALREADY_LOGOUT);
      }
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.warn("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
      throw new JwtException("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.warn("Expired JWT token, 만료된 JWT token 입니다.");
      throw new JwtException("Expired JWT token, 만료된 JWT token 입니다.");
    } catch (UnsupportedJwtException e) {
      log.warn("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
      throw new JwtException("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      log.warn("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
      throw new JwtException("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
    String[] authorities = claims.get("authorityType").toString().split(",");
    return Arrays.stream(authorities)
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toSet());
  }

  private Claims getParseClaims(String accessToken) {
    try {
      return getJwtParseSignedClaims(accessToken).getPayload();
    } catch (Exception e) {
      return ((ExpiredJwtException) e).getClaims();
    }
  }

  private Jws<Claims> getJwtParseSignedClaims(String accessToken) {
    return Jwts.parser()
      .verifyWith(jwtProperties.getSecretKey())
      .build()
      .parseSignedClaims(accessToken);
  }


}
