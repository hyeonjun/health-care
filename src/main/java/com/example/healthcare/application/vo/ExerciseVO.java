package com.example.healthcare.application.vo;

import com.example.healthcare.application.account.domain.code.AuthorityType;
import com.example.healthcare.application.exercise.domain.code.ExerciseBodyType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseVO {

  private Long id;
  private String name;
  private boolean isDeleted;
  private ExerciseBodyType exerciseBodyType;

  private Long userId;
  private AuthorityType authorityType;

  @QueryProjection
  public ExerciseVO(Long id, String name, boolean isDeleted,
    ExerciseBodyType exerciseBodyType, Long userId, AuthorityType authorityType) {
    this.id = id;
    this.name = name;
    this.isDeleted = isDeleted;
    this.exerciseBodyType = exerciseBodyType;
    this.userId = userId;
    this.authorityType = authorityType;
  }
}
