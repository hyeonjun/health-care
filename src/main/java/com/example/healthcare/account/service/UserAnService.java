package com.example.healthcare.account.service;

import com.example.healthcare.account.domain.User;
import com.example.healthcare.account.repository.UserRepository;
import com.example.healthcare.account.service.dto.CreateUserDTO;
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
  public void signUp(CreateUserDTO dto) {
    String encodedPassword = passwordEncoder.encode(dto.newPassword());
    User user = new User(dto, encodedPassword);
    userRepository.save(user);
  }
}
