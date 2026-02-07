package com.project.fitness.controller;

import com.project.fitness.entity.CardioWorkout;
import com.project.fitness.entity.StrengthWorkout;
import com.project.fitness.entity.WorkoutRoutine;
import com.project.fitness.repository.WorkoutRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin(origins = "*")
public class WorkoutController {

    private final WorkoutRepository repository;

    public WorkoutController(WorkoutRepository repository) {
        this.repository = repository;
    }

    // 1. GET ALL
    @GetMapping
    public List<WorkoutRoutine> getAll() {
        return repository.findAll();
    }

    // 2. GET BY ID
    @GetMapping("/{id}")
    public WorkoutRoutine getOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    // 3. CREATE CARDIO
    @PostMapping("/cardio")
    public String createCardio(@RequestBody CardioRequest dto) {
        CardioWorkout workout = new CardioWorkout(null, dto.userId, dto.name, dto.duration, dto.distance);
        repository.save(workout);
        return "Cardio Workout Created!";
    }

    // 4. CREATE STRENGTH
    @PostMapping("/strength")
    public String createStrength(@RequestBody StrengthRequest dto) {
        StrengthWorkout workout = new StrengthWorkout(null, dto.userId, dto.name, dto.duration, dto.weight, dto.reps);
        repository.save(workout);
        return "Strength Workout Created!";
    }

    // 5. UPDATE (Пример обновления Кардио)
    @PutMapping("/{id}/cardio")
    public String updateCardio(@PathVariable Long id, @RequestBody CardioRequest dto) {
        CardioWorkout workout = new CardioWorkout(id, dto.userId, dto.name, dto.duration, dto.distance);
        repository.update(id, workout);
        return "Workout Updated!";
    }

    // 6. DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repository.delete(id);
        return "Workout Deleted!";
    }

    // DTO классы для JSON
    static class CardioRequest {
        public Long userId;
        public String name;
        public int duration;
        public double distance;
    }

    static class StrengthRequest {
        public Long userId;
        public String name;
        public int duration;
        public double weight;
        public int reps;
    }
}