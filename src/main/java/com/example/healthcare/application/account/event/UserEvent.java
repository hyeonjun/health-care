package com.example.healthcare.application.account.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public final class UserEvent extends ApplicationEvent {

  private final Type type;
  private final String email;

  private UserEvent(Object source, Type type, String email) {
    super(source);
    // Object source 는 이벤트를 게시하는 클래스의 객체를 의미
    // 예를 들어 UserService 클래스에서 UserEvent 객체를 생성/게시할 때 UserService 객체를 Object source 인자로 넘김
    // ApplicationEvent 객체에 할당된 Object source 는 Object getSource() 로 참조 가능
    this.type = type;
    this.email = email;
  }

  // 정적 팩토리 메서드 패턴
  public static UserEvent logIn(Object source, String email) {
    return new UserEvent(source, Type.LOGIN, email);
  }

  public enum Type {
    LOGIN
  }
}
