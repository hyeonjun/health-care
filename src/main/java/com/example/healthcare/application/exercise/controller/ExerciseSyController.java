package com.example.healthcare.application.exercise.controller;

import com.example.healthcare.application.common.response.CommonResponse;
import com.example.healthcare.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.healthcare.application.exercise.controller.dto.SearchExerciseDTO;
import com.example.healthcare.application.exercise.service.ExerciseSyService;
import com.example.healthcare.application.vo.ExerciseDetailVO;
import com.example.healthcare.application.vo.ExerciseVO;
import com.example.healthcare.infra.config.security.user.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public CommonResponse<Void> createExercise(@Valid @RequestBody CreateExerciseDTO dto,
    @AuthenticationPrincipal LoginUser loginUser) {
    exerciseSyService.createExercise(loginUser, dto);
    return CommonResponse.success();
  }

  @GetMapping
  public CommonResponse<Page<ExerciseVO>> getExercises(@Valid SearchExerciseDTO dto) {
    return CommonResponse.success(exerciseSyService.getExercises(dto));
  }

  @GetMapping("/{exercise-id}")
  public CommonResponse<ExerciseDetailVO> getExerciseDetail(@PathVariable(value = "exercise-id") Long exerciseId) {
    return CommonResponse.success(exerciseSyService.getExercise(exerciseId));
  }
}
