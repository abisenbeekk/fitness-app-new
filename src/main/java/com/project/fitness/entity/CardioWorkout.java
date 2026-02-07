package com.project.fitness.entity;

public class CardioWorkout extends WorkoutRoutine {

    private double distanceKm;

    public CardioWorkout(Long id, Long userId, String name, int duration, double distanceKm) {
        super(id, userId, name, duration, "CARDIO");
        this.distanceKm = distanceKm;
    }

    @Override
    public String getWorkoutType() {
        return "CARDIO";
    }

    public double getDistanceKm() { return distanceKm; }
}