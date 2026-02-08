package com.project.fitness.repository;

import com.project.fitness.entity.CardioWorkout;
import com.project.fitness.entity.StrengthWorkout;
import com.project.fitness.entity.WorkoutRoutine;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class WorkoutRepository {

    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "postgres";
    private final String PASS = "ayau123";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void save(WorkoutRoutine workout) {
        String sql = "INSERT INTO workouts (user_id, name, duration_minutes, workout_type, distance_km, weight_kg, repetitions) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, workout.getUserId());
            stmt.setString(2, workout.getName());
            stmt.setInt(3, workout.getDurationMinutes());
            stmt.setString(4, workout.getType());

            if (workout instanceof CardioWorkout) {
                stmt.setDouble(5, ((CardioWorkout) workout).getDistanceKm());
                stmt.setObject(6, null);
                stmt.setObject(7, null);
            } else if (workout instanceof StrengthWorkout) {
                stmt.setObject(5, null);
                stmt.setDouble(6, ((StrengthWorkout) workout).getWeightKg());
                stmt.setInt(7, ((StrengthWorkout) workout).getRepetitions());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<WorkoutRoutine> findAll() {
        List<WorkoutRoutine> workouts = new ArrayList<>();
        String sql = "SELECT * FROM workouts";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                workouts.add(mapRowToWorkout(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workouts;
    }

    public Optional<WorkoutRoutine> findById(Long id) {
        String sql = "SELECT * FROM workouts WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapRowToWorkout(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void update(Long id, WorkoutRoutine workout) {
        String sql = "UPDATE workouts SET name=?, duration_minutes=?, distance_km=?, weight_kg=?, repetitions=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, workout.getName());
            stmt.setInt(2, workout.getDurationMinutes());

            if (workout instanceof CardioWorkout) {
                stmt.setDouble(3, ((CardioWorkout) workout).getDistanceKm());
                stmt.setObject(4, null);
                stmt.setObject(5, null);
            } else if (workout instanceof StrengthWorkout) {
                stmt.setObject(3, null);
                stmt.setDouble(4, ((StrengthWorkout) workout).getWeightKg());
                stmt.setInt(5, ((StrengthWorkout) workout).getRepetitions());
            }

            stmt.setLong(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM workouts WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private WorkoutRoutine mapRowToWorkout(ResultSet rs) throws SQLException {
        String type = rs.getString("workout_type");
        Long id = rs.getLong("id");
        Long uId = rs.getLong("user_id");
        String name = rs.getString("name");
        int dur = rs.getInt("duration_minutes");

        if ("CARDIO".equals(type)) {
            return new CardioWorkout(id, uId, name, dur, rs.getDouble("distance_km"));
        } else if ("STRENGTH".equals(type)) {
            return new StrengthWorkout(id, uId, name, dur, rs.getDouble("weight_kg"), rs.getInt("repetitions"));
        }
        return null;
    }
}