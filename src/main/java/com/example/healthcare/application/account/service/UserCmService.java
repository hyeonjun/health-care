package com.example.healthcare.application.account.service;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.repository.UserRepository;
import com.example.healthcare.application.common.exception.AuthException;
import com.example.healthcare.application.common.exception.AuthException.AuthExceptionCode;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.healthcare.infra.config.security.user.LoginUser;
import com.example.healthcare.application.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCmService {

  private final UserRepository userRepository;

  public UserVO getUser(LoginUser loginUser, Long userId) {
    if (!loginUser.getId().equals(userId)) {
      throw new AuthException(AuthExceptionCode.NOT_AUTHORIZED);
    }

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    return UserVO.valueOf(user);
  }

}
