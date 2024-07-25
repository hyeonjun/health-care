package com.example.healthcare.application.exercise.domain;

import com.example.healthcare.application.exercise.domain.code.ExerciseSetType;
import com.example.healthcare.application.exercise.domain.code.WeightUnitType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExerciseSet {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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

}
