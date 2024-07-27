package com.example.healthcare.application.vo;

import com.example.healthcare.application.exercise.domain.UserExerciseLog;
import com.example.healthcare.application.exercise.domain.code.ExerciseTimeType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExerciseLogDetailVO extends UserExerciseLogSummaryVO {

  private Page<UserExerciseRoutineVO> routineList;

  @Builder(access = AccessLevel.PRIVATE)
  protected UserExerciseLogDetailVO(LocalDate exerciseDate, Long exerciseLogId, Integer exerciseCount,
    Long exerciseTime, boolean isDeleted, BigInteger totalSetCount, BigInteger totalWeight,
    BigInteger totalReps, BigInteger totalTime, ExerciseTimeType exerciseTimeType,
    Page<UserExerciseRoutineVO> routineList) {
    super(exerciseDate, exerciseLogId, exerciseCount, exerciseTime, isDeleted,
      totalSetCount, totalWeight, totalReps, totalTime, exerciseTimeType);
    this.routineList = routineList;
  }

  public static UserExerciseLogDetailVO valueOf(UserExerciseLog userExerciseLog, Page<UserExerciseRoutineVO> routineList) {
    return builder()
      .exerciseLogId(userExerciseLog.getId())
      .exerciseCount(userExerciseLog.getExerciseCount())
      .exerciseTime(userExerciseLog.getExerciseTime())
      .totalSetCount(userExerciseLog.getTotalSetCount())
      .totalWeight(userExerciseLog.getTotalWeight())
      .totalReps(userExerciseLog.getTotalReps())
      .totalTime(userExerciseLog.getTotalTime())
      .exerciseDate(userExerciseLog.getExerciseDate())
      .exerciseTimeType(userExerciseLog.getExerciseTimeType())
      .routineList(routineList)
      .build();
  }
}
