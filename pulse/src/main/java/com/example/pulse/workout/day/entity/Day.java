package com.example.pulse.workout.day.entity;

import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.plan.entity.Plan;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "day")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "plan_id")
    @JsonBackReference
    private Plan plan;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "day_exercise",joinColumns = @JoinColumn(name = "day_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private List<Exercise> exercises;

    public Day(String name, Plan plan, List<Exercise> exercises) {
        this.name = name;
        this.plan = plan;
        this.exercises = exercises;
    }

    public Day(int id, String name, Plan plan, List<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.plan = plan;
        this.exercises = exercises;
    }

    public Day(String name, Plan plan, int id) {
        this.name = name;
        this.plan = plan;
        this.id = id;
    }

    public Day(String name, Plan plan) {
        this.name = name;
        this.plan = plan;
    }

    public Day(String name) {
        this.name = name;
    }

    public Day() {
    }

    public Day(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", plan=" + plan +
                ", exercises=" + exercises +
                '}';
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
