package com.example.healthcare.application.exercise.domain;

import com.example.healthcare.application.common.domain.Base;
import com.example.healthcare.application.exercise.domain.code.ExerciseSetType;
import com.example.healthcare.application.exercise.domain.code.WeightUnitType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @Enumerated(value = EnumType.STRING)
  @Column(name = "weight_unit_type", length = 191)
  private WeightUnitType weightUnitType;
  private Long wight;
  private Integer reps;
  private Long time;

  private boolean complete;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_exercise_routine_id", nullable = false, referencedColumnName = "user_exercise_routine_id")
  @JsonBackReference
  private UserExerciseRoutine userExerciseRoutine;

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
      .append("weightUnitType", weightUnitType)
      .append("wight", wight)
      .append("reps", reps)
      .append("time", time)
      .append("complete", complete)
      .append("userExerciseRoutine", userExerciseRoutine)
      .append("createdDateTime", createdDateTime)
      .append("updatedDateTime", updatedDateTime)
      .toString();
  }
}
