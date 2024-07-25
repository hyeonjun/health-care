package com.example.healthcare.application.auth.domain;

import com.example.healthcare.application.common.exception.AuthException;
import com.example.healthcare.application.common.exception.AuthException.AuthExceptionCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = 31 * 60) // 31분
public class RefreshToken {

  @Id
  private String email;
  private String refreshToken;

  @Builder(builderMethodName = "createRefreshToken")
  private RefreshToken(String email, String refreshToken) {
    this.email = email;
    this.refreshToken = refreshToken;
  }

  public boolean validate(String refreshToken) {
    if (!this.refreshToken.equals(refreshToken)) {
      throw new AuthException(AuthExceptionCode.JWT_REFRESH_TOKEN_VERIFICATION_FAIL);
    }
    return true;
  }
}