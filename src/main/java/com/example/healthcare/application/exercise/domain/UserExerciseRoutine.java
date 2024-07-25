package com.example.healthcare.application.exercise.domain;

import com.example.healthcare.application.common.domain.Base;
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
public class UserExerciseRoutine extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_exercise_routine_id")
  private Long id;

  private Long restTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_exercise_log_id", nullable = false, referencedColumnName = "user_exercise_log_id")
  @JsonBackReference
  private UserExerciseLog userExerciseLog;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exercise_id", nullable = false, referencedColumnName = "exercise_id")
  @JsonBackReference
  private Exercise exercise;
}
