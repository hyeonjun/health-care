package com.example.healthcare.application.account.service;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.repository.UserRepository;
import com.example.healthcare.application.account.service.dto.ChangeAuthorityDTO;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSyService {

  private final UserRepository userRepository;


  @Transactional
  public void changeAuthority(Long userId, ChangeAuthorityDTO dto) {
    User user = userRepository.findById(userId).orElseThrow(
      () -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    if (user.getAuthorityType().equals(dto.authorityType())) {
      return;
    }

    user.changeAuthority(dto.authorityType());
    userRepository.save(user);
  }
}