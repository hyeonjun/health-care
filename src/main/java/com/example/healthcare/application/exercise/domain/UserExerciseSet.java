package com.example.healthcare.application.exercise.domain;

import com.example.healthcare.application.common.domain.Base;
import com.example.healthcare.application.exercise.controller.dto.ExerciseSetDTO;
import com.example.healthcare.application.exercise.domain.code.ExerciseSetType;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity
@DynamicUpdate
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExerciseSet extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_exercise_set_id")
  private Long id;

  @Column(name = "setNumber", nullable = false)
  private Long setNumber;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "exercise_set_type", length = 191)
  private ExerciseSetType exerciseSetType;

  private Long weight;
  private Integer reps;
  private Long time;

  private boolean complete;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_exercise_routine_id", nullable = false, referencedColumnName = "user_exercise_routine_id")
  @JsonBackReference
  private UserExerciseRoutine userExerciseRoutine;

  public static UserExerciseSet createSet(ExerciseSetDTO dto) {
    return builder()
      .setNumber(dto.getSetNumber())
      .exerciseSetType(dto.getSetType())
      .weight(dto.getWeight())
      .reps(dto.getReps())
      .time(dto.getTime())
      .complete(dto.getComplete())
      .build();
  }

  public void applyRoutine(UserExerciseRoutine routine) {
    this.userExerciseRoutine = routine;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserExerciseSet that)) return false;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
      .append("id", id)
      .append("setNumber", setNumber)
      .append("exerciseSetType", exerciseSetType)
      .append("weight", weight)
      .append("reps", reps)
      .append("time", time)
      .append("complete", complete)
      .append("userExerciseRoutine", userExerciseRoutine)
      .append("createdDateTime", createdDateTime)
      .append("updatedDateTime", updatedDateTime)
      .toString();
  }
}
