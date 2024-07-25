package com.example.healthcare.event.user;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.repository.UserRepository;
import com.example.healthcare.application.common.exception.InvalidInputValueException;
import com.example.healthcare.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener implements ApplicationListener<UserEvent> {

  private final UserRepository userRepository;

  @Async
  @Transactional
  @Override
  public void onApplicationEvent(UserEvent event) {
    User user = userRepository.findByEmail(event.getEmail())
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    switch (event.getType()) {
      case LOGIN -> user.updateSignInDateTime(LocalDateTime.now());
      default -> throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }

    log.info("Listener User Login Event: {}", user);
    userRepository.save(user);
  }
}
