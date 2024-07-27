package com.example.healthcare.application.exercise.domain;

import com.example.healthcare.application.common.domain.Base;
import com.example.healthcare.application.exercise.controller.dto.CreateUserExerciseRoutineDTO;
import com.example.healthcare.application.exercise.domain.code.WeightUnitType;
import com.example.healthcare.application.exercise.service.data.UserExerciseData.UserExerciseRoutineData;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigInteger;
import java.util.Objects;

@Entity
@DynamicUpdate
@Getter
@Table(name = "user_exercise_routine",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_exercise_routine_id", "order_number"})
  }
)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExerciseRoutine extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_exercise_routine_id")
  private Long id;

  private Long restTime;
  @Column(name = "order_number")
  private Integer order;

  // 운동 수행 데이터
  private Integer setCount;
  private BigInteger sumWeight;
  private BigInteger sumReps;
  private BigInteger sumTime;


  @Enumerated(value = EnumType.STRING)
  @Column(name = "weight_unit_type", length = 191)
  private WeightUnitType weightUnitType = WeightUnitType.KILOGRAM;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_exercise_log_id", nullable = false, referencedColumnName = "user_exercise_log_id")
  @JsonBackReference
  private UserExerciseLog userExerciseLog;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exercise_id", nullable = false, referencedColumnName = "exercise_id")
  @JsonBackReference
  private Exercise exercise;

  public static UserExerciseRoutine createRoutine(CreateUserExerciseRoutineDTO dto,
    UserExerciseRoutineData routineData, Exercise exercise) {
    return builder()
      .restTime(dto.restTime())
      .order(dto.order())
      .setCount(routineData.setCount)
      .sumWeight(routineData.sumWeight)
      .sumReps(routineData.sumReps)
      .sumTime(routineData.sumTime)
      .weightUnitType(dto.weightUnitType() != null ? dto.weightUnitType() : WeightUnitType.KILOGRAM)
      .exercise(exercise)
      .build();
  }

  public void applyLog(UserExerciseLog userExerciseLog) {
    this.userExerciseLog = userExerciseLog;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserExerciseRoutine that)) return false;
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
      .append("id", id)
      .append("restTime", restTime)
      .append("setCount", setCount)
      .append("order", order)
      .append("sumWeight", sumWeight)
      .append("sumReps", sumReps)
      .append("sumTime", sumTime)
      .append("weightUnitType", weightUnitType)
      .append("userExerciseLog", userExerciseLog)
      .append("exercise", exercise)
      .append("createdDateTime", createdDateTime)
      .append("updatedDateTime", updatedDateTime)
      .toString();
  }
}
