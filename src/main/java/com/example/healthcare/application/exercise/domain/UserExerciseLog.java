package com.example.healthcare.application.exercise.domain;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.exercise.domain.code.ExerciseTimeType;
import com.example.healthcare.application.common.domain.Base;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_exercise_log",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"exercise_date", "exercise_time_type", "user_id"})
  }
)
public class UserExerciseLog extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_exercise_log_id")
  private Long id;

  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;

  @Column(name = "exercise_date", nullable = false)
  private LocalDate exerciseDate;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "exercise_time_type", length = 191)
  private ExerciseTimeType exerciseTimeType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
  @JsonBackReference
  private User user;

}
