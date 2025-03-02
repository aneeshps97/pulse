package com.example.pulse.workout.exercise.entity;

import com.example.pulse.workout.day.entity.Day;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "day_id")
    @JsonBackReference
    private Day day;

    public Exercise(String name, Day day) {
        this.name = name;
        this.day = day;
    }

    public Exercise(int id, String name, Day day) {
        this.id = id;
        this.name = name;
        this.day = day;
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

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
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
