package com.example.healthcare.account.service;

import com.example.healthcare.account.repository.UserRepository;
import com.example.healthcare.account.service.dto.SignUpDTO;
import com.example.healthcare.util.PasswordProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserAnService {

  private final UserRepository userRepository;
  private final PasswordProvider passwordEncoder;

  @Transactional
  public void signUp(SignUpDTO signUpDTO) {
  }
}
