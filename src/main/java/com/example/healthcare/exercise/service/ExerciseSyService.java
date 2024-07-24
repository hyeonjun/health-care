package com.example.healthcare.exercise.service;

import com.example.healthcare.common.response.CommonResponse;
import com.example.healthcare.exercise.domain.Exercise;
import com.example.healthcare.exercise.repository.ExerciseRepository;
import com.example.healthcare.exercise.service.dto.CreateSyExerciseDTO;
import com.example.healthcare.util.AdminCheck;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseSyService {

    private final ExerciseRepository exerciseRepository;
    private final AdminCheck adminCheck;

    @Transactional
    public void registration(CreateSyExerciseDTO dto) {
        adminCheck.adminPage();
        Exercise exercise = new Exercise(dto);
        exerciseRepository.save(exercise);
    }
}
