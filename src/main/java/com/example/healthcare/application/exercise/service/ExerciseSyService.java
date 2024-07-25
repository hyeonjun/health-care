package com.example.healthcare.application.exercise.service;

import com.example.healthcare.application.exercise.domain.Exercise;
import com.example.healthcare.application.exercise.repository.ExerciseRepository;
import com.example.healthcare.application.exercise.service.dto.CreateSyExerciseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseSyService {

    private final ExerciseRepository exerciseRepository;

    @Transactional
    public void registration(CreateSyExerciseDTO dto) {
        Exercise exercise = new Exercise(dto);
        exerciseRepository.save(exercise);
    }
}
