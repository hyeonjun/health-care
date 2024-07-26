package com.example.healthcare.application.account.service;

import com.example.healthcare.application.account.controller.dto.ChangeAuthorityDTO;
import com.example.healthcare.application.account.controller.dto.SearchUserDTO;
import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.repository.UserRepository;
import com.example.healthcare.application.account.repository.param.SearchUserParam;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.healthcare.application.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSyService {

  private final UserRepository userRepository;

  public Page<UserVO> getUsers(SearchUserDTO dto) {
    return userRepository.findAll(SearchUserParam.valueOf(dto), dto.toPageRequest());
  }

  @Transactional
  public void changeAuthority(Long userId, ChangeAuthorityDTO dto) {
    User user = userRepository.findById(userId).orElseThrow(
      () -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    if (user.getAuthorityType().equals(dto.authorityType())) {
      return;
    }

    user.updateAuthority(dto.authorityType());
    userRepository.save(user);
  }
}
