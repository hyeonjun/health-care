package com.example.healthcare.application.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExerciseLogVO {

  private LocalDate exerciseDate;

  @QueryProjection
  public UserExerciseLogVO(LocalDate exerciseDate) {
    this.exerciseDate = exerciseDate;
  }
}
