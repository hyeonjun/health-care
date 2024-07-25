package com.example.healthcare.application.exercise.domain;

import com.example.healthcare.application.common.domain.Base;
import com.example.healthcare.application.exercise.domain.code.ExerciseType;
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
public class ExerciseTypeRelation extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "exercise_type_relation_id")
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "exercise_type", length = 191)
  private ExerciseType exerciseType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exercise_id", nullable = false, referencedColumnName = "exercise_id")
  @JsonBackReference
  private Exercise exercise;

}
