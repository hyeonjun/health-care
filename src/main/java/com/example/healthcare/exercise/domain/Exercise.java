package com.example.healthcare.exercise.domain;

import com.example.healthcare.common.domain.Base;
import com.example.healthcare.exercise.domain.code.ExerciseBodyType;
import com.example.healthcare.exercise.domain.code.ExerciseToolType;
import com.example.healthcare.exercise.service.dto.CreateSyExerciseDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "exercise_id")
  private Long id;

  @Column(name = "name", nullable = false, length = 191)
  private String name;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "exercise_body_type", nullable = false, length = 191)
  private ExerciseBodyType bodyType;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "exercise_tool_type", nullable = false, length = 191)
  private ExerciseToolType toolType;

  public Exercise(CreateSyExerciseDTO dto){
   this.name = dto.name();
   this.bodyType = dto.bodyType();
   this.toolType = dto.toolType();
  }


}
