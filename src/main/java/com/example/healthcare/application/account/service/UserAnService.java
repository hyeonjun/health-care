package com.example.healthcare.application.account.service;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.repository.UserRepository;
import com.example.healthcare.application.account.controller.dto.CreateUserDTO;
import com.example.healthcare.application.common.exception.DuplicateException;
import com.example.healthcare.application.common.exception.DuplicateException.DuplicateExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserAnService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void signUp(CreateUserDTO dto) {
    if (userRepository.findByEmail(dto.email()).isPresent()) {
      throw new DuplicateException(DuplicateExceptionCode.DUPLICATE_EMAIL);
    }

    String encodedPassword = passwordEncoder.encode(dto.newPassword());
    User user = new User(dto, encodedPassword);
    userRepository.save(user);
  }



}
