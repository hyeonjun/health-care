package com.example.healthcare.application.account.helper;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.common.exception.AuthException;
import com.example.healthcare.application.common.exception.AuthException.AuthExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHelper {

  public void checkAuthorization(User loginUser, User resourceUser) {
    if (!loginUser.equals(resourceUser)) {
      throw new AuthException(AuthExceptionCode.NOT_AUTHORIZED);
    }
  }
}
