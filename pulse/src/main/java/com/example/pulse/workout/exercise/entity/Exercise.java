package com.example.pulse.workout.exercise.entity;

import com.example.pulse.workout.day.entity.Day;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercise")
public class Exercise {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "exercises")
    @JsonBackReference
    List<Day> days = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    public Exercise(int id, String name, List<Day> days) {
        this.id = id;
        this.name = name;
        this.days = days;
    }

    public Exercise(String name, List<Day> days) {
        this.name = name;
        this.days = days;
    }

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Exercise() {
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
