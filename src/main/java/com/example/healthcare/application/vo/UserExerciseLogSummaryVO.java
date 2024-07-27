package com.example.healthcare.application.vo;

import com.example.healthcare.application.exercise.domain.code.ExerciseTimeType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExerciseLogSummaryVO extends  UserExerciseLogVO {

  private Long exerciseLogId;
  private Integer exerciseCount; // 운동한 종목 개수
  private Long exerciseTime; // 운동한 시간
  private boolean isDeleted;

  // 운동 수행 총 데이터
  private BigInteger totalSetCount;
  private BigInteger totalWeight;
  private BigInteger totalReps;
  private BigInteger totalTime;

  private ExerciseTimeType exerciseTimeType;

  @QueryProjection
  public UserExerciseLogSummaryVO(LocalDate exerciseDate, Long exerciseLogId, Integer exerciseCount, Long exerciseTime, boolean isDeleted, BigInteger totalSetCount, BigInteger totalWeight, BigInteger totalReps, BigInteger totalTime, ExerciseTimeType exerciseTimeType) {
    super(exerciseDate);
    this.exerciseLogId = exerciseLogId;
    this.exerciseCount = exerciseCount;
    this.exerciseTime = exerciseTime;
    this.isDeleted = isDeleted;
    this.totalSetCount = totalSetCount;
    this.totalWeight = totalWeight;
    this.totalReps = totalReps;
    this.totalTime = totalTime;
    this.exerciseTimeType = exerciseTimeType;
  }
}
