package com.project.fitness.entity;

public class StrengthWorkout extends WorkoutRoutine {
    private double weightKg;
    private int repetitions;

    public StrengthWorkout(Long id, Long userId, String name, int duration, double weightKg, int reps) {
        super(id, userId, name, duration, "STRENGTH");
        this.weightKg = weightKg;
        this.repetitions = reps;
    }
    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    @Override
    public String getWorkoutType() {
        return "STRENGTH";
    }

    public double getWeightKg() { return weightKg; }
}
