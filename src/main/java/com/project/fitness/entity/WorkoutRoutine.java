package com.project.fitness.entity;

public abstract class WorkoutRoutine {

    protected Long id;
    protected Long userId;
    protected String name;
    protected int durationMinutes;
    protected String type;


    public WorkoutRoutine(Long id, Long userId, String name, int durationMinutes, String type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.type = type;
    }

    public WorkoutRoutine() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public abstract String getWorkoutType();

    public Long getUserId() { return userId; }
    public String getName() { return name; }
}