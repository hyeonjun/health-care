package com.example.healthcare.auth.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "accessTokenBlacklist", timeToLive = 11 * 60) // 11분
public class AccessTokenBlackList {

  @Id
  private String accessToken;

  @Builder(builderMethodName = "createaAcessTokenBlacklist")
  public AccessTokenBlackList(String accessToken) {
    this.accessToken = accessToken;
  }
}
