package com.example.healthcare.application.exercise.controller;

import com.example.healthcare.application.exercise.service.ExerciseSyService;
import com.example.healthcare.application.exercise.controller.dto.CreateSyExerciseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sy/exercises")
public class ExerciseSyController {

  private final ExerciseSyService exerciseSyService;

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void registration(@Valid @RequestBody CreateSyExerciseDTO dto) {
    exerciseSyService.registration(dto);
  }
}
