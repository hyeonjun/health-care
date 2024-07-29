package com.example.healthcare.application.exercise.domain;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.common.domain.Base;
import com.example.healthcare.application.exercise.controller.dto.CreateUserExerciseLogDTO;
import com.example.healthcare.application.exercise.controller.dto.UpdateUserExerciseLogDTO;
import com.example.healthcare.application.exercise.domain.code.ExerciseTimeType;
import com.example.healthcare.application.exercise.service.data.UserExerciseData.UserExerciseLogData;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@DynamicUpdate
@Getter
@Table(name = "user_exercise_log",
  indexes = {@Index(columnList = "exercise_date, user_id")}
)
@SQLDelete(sql = "UPDATE user_exercise_log SET is_deleted = true where user_exercise_log_id = ?")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExerciseLog extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_exercise_log_id")
  private Long id;

  private Integer exerciseCount;
  private Long exerciseTime;
  private boolean isDeleted;

  // 운동 수행 데이터
  private BigInteger totalSetCount;
  private BigInteger totalWeight;
  private BigInteger totalReps;
  private BigInteger totalTime;

  @Column(name = "exercise_date", nullable = false)
  private LocalDate exerciseDate;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "exercise_time_type", length = 191)
  private ExerciseTimeType exerciseTimeType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
  @JsonBackReference
  private User user;

  public static UserExerciseLog createLog(CreateUserExerciseLogDTO dto, User user, UserExerciseLogData data) {
    return builder()
      .exerciseCount(data.exerciseCount)
      .exerciseTime(dto.exerciseTime())
      .isDeleted(false)
      .totalSetCount(data.totalSetCount)
      .totalWeight(data.totalWeight)
      .totalReps(data.totalReps)
      .totalTime(data.totalTime)
      .exerciseDate(dto.exerciseDate())
      .exerciseTimeType(dto.exerciseTimeType())
      .user(user)
      .build();
  }

  public void updateLog(UpdateUserExerciseLogDTO dto, UserExerciseLogData data) {
    this.exerciseCount = data.exerciseCount;
    this.exerciseTime = dto.exerciseTime();
    this.totalSetCount = data.totalSetCount;
    this.totalWeight = data.totalWeight;
    this.totalReps = data.totalReps;
    this.totalTime = data.totalTime;

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserExerciseLog that)) return false;
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
      .append("exerciseCount", exerciseCount)
      .append("exerciseTime", exerciseTime)
      .append("isDeleted", isDeleted)
      .append("totalSetCount", totalSetCount)
      .append("totalWeight", totalWeight)
      .append("totalReps", totalReps)
      .append("totalTime", totalTime)
      .append("exerciseDate", exerciseDate)
      .append("exerciseTimeType", exerciseTimeType)
      .append("user", user)
      .append("createdDateTime", createdDateTime)
      .append("updatedDateTime", updatedDateTime)
      .toString();
  }
}
