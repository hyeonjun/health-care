package com.example.healthcare.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

@Slf4j
public class MockUtil {

  public static <T> T createEntityInstance(Class<T> clazz) {
    try {
      // 기본 생성자를 가져옴
      Constructor<T> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);

      // 객체 생성
      return constructor.newInstance();
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public static <T> void setEntityFieldValue(T entity, String fieldName, Object value) {
    try {
      // 필드를 가져옴
      Field field = entity.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);

      // 필드 값 설정
      field.set(entity, value);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

}
