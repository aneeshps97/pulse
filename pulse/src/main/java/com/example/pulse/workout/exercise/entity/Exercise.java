package com.example.pulse.workout.exercise.entity;

import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.log.entity.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercise")
public class Exercise {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "exercises")
    @JsonIgnore
    List<Log> logs = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "exercises")
    @JsonIgnore
    List<Day> days = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    public Exercise(List<Log> logs, List<Day> days, int id, String name) {
        this.logs = logs;
        this.days = days;
        this.id = id;
        this.name = name;
    }
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

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
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
