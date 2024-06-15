package com.example.healthcare.account.service;

import com.example.healthcare.account.repository.UserRepository;
import com.example.healthcare.account.service.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserAnService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void signUp(SignUpDTO signUpDTO) {

  }
}
