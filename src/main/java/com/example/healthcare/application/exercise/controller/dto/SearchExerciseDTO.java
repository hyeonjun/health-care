package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.exercise.domain.code.ExerciseBodyType;
import com.example.healthcare.util.PagingDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchExerciseDTO extends PagingDTO {

  private String name;
  private ExerciseBodyType exerciseBodyType;
  private Boolean exerciseDeleted;

}
