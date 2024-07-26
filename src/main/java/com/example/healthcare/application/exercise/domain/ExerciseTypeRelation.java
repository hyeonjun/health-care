package com.example.healthcare.application.exercise.domain;

import com.example.healthcare.application.common.domain.Base;
import com.example.healthcare.application.exercise.domain.code.ExerciseType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
public class ExerciseTypeRelation extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "exercise_type_relation_id")
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "exercise_type", nullable = false, length = 191)
  private ExerciseType exerciseType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exercise_id", nullable = false, referencedColumnName = "exercise_id")
  @JsonBackReference
  private Exercise exercise;

  public static ExerciseTypeRelation createExerciseTypeRelation(ExerciseType exerciseType, Exercise exercise) {
    return ExerciseTypeRelation.builder()
      .exerciseType(exerciseType)
      .exercise(exercise)
      .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ExerciseTypeRelation that)) return false;
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
      .append("exerciseType", exerciseType)
      .append("exercise", exercise != null ? exercise.getId() : null)
      .append("createdDateTime", createdDateTime)
      .append("updatedDateTime", updatedDateTime)
      .toString();
  }
}
