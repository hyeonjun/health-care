package com.example.healthcare.config.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@Getter
public class JwtProperties {

  private final SecretKey secretKey;
  private final Long accessTokenExpMills;
  private final Long refreshTokenExpMills;

  public JwtProperties(
    @Value("${jwt.secret.key}") String secretKey,
    @Value("${jwt.token.access.expiration_time}") Long accessTokenExpMin,
    @Value("${jwt.token.refresh.expiration_time}") Long refreshTokenExpMin) {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    this.accessTokenExpMills = accessTokenExpMin * 1000 * 60;
    this.refreshTokenExpMills = refreshTokenExpMin * 1000 * 60;
  }
}
