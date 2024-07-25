package com.example.healthcare.event.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publishEventUserLogin(String email) {
    UserEvent userEvent = UserEvent.logIn(this, email);
    log.info("Publish User Login Event: {}", userEvent);

    // UserEvent 이벤트 메시지 객체 게시
    applicationEventPublisher.publishEvent(userEvent);
  }
}
