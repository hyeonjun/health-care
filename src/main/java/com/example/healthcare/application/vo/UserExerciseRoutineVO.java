package com.example.healthcare.application.vo;

import com.example.healthcare.application.exercise.domain.code.ExerciseBodyType;
import com.example.healthcare.application.exercise.domain.code.WeightUnitType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.math.BigInteger;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExerciseRoutineVO {

  private Long routineId;
  private Long restTime;
  private Integer order;
  private Integer setCount;
  private BigInteger sumWeight;
  private BigInteger sumReps;
  private BigInteger sumTime;
  private WeightUnitType weightUnitType;
  private Long exerciseId;
  private String exerciseName;
  private ExerciseBodyType exerciseBodyType;
  @Setter
  private Page<UserExerciseSetVO> setList;

  @QueryProjection
  public UserExerciseRoutineVO(Long routineId, Long restTime, Integer order, Integer setCount,
    BigInteger sumWeight, BigInteger sumReps, BigInteger sumTime, WeightUnitType weightUnitType, Long exerciseId,
    String exerciseName, ExerciseBodyType exerciseBodyType) {
    this.routineId = routineId;
    this.restTime = restTime;
    this.order = order;
    this.setCount = setCount;
    this.sumWeight = sumWeight;
    this.sumReps = sumReps;
    this.sumTime = sumTime;
    this.weightUnitType = weightUnitType;
    this.exerciseId = exerciseId;
    this.exerciseName = exerciseName;
    this.exerciseBodyType = exerciseBodyType;
  }
}
