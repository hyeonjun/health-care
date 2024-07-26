package com.example.healthcare.infra.config.security.jwt;

import com.example.healthcare.infra.config.security.user.LoginUser;
import com.example.healthcare.application.vo.TokenVO;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Jwts.SIG.HS256;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

  private final JwtProperties jwtProperties;

  public TokenVO createToken(LoginUser loginUser) {
    Date now = new Date();

    return TokenVO.valueOf(loginUser,
      createAccessToken(loginUser, now),
      createRefreshToken(now));
  }

  public String createAccessToken(LoginUser loginUser, Date now) {
    String authorityType = loginUser.getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    return Jwts.builder()
      .claim("id", loginUser.getId())
      .claim("email", loginUser.getEmail())
      .claim("name", loginUser.getName())
      .claim("authorityType", authorityType)
      .issuedAt(now)
      .expiration(new Date(now.getTime() + jwtProperties.getAccessTokenExpMills()))
      .signWith(jwtProperties.getSecretKey(), HS256)
      .compact();
  }

  public String createRefreshToken(Date now) {
    return Jwts.builder()
      .expiration(new Date(now.getTime() + jwtProperties.getRefreshTokenExpMills()))
      .signWith(jwtProperties.getSecretKey(), HS256)
      .compact();
  }
}
