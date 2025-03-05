package com.example.pulse.workout.log.entity;

import com.example.pulse.workout.exercise.entity.Exercise;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private Date date;
    @Column(name = "weight")
    private String weight;
    @Column(name = "reps")
    private int reps;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "log_exercise", joinColumns = @JoinColumn(name = "log_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private List<Exercise> exercises;

    public Log(Date date, String weight, int reps, List<Exercise> exercises) {
        this.date = date;
        this.weight = weight;
        this.reps = reps;
        this.exercises = exercises;
    }

    public Log(int id, Date date, String weight, int reps, List<Exercise> exercises) {
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.reps = reps;
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
