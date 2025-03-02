package com.example.pulse.workout.plan.entity;

import com.example.pulse.workout.day.entity.Day;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "plan", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<Day> days;


    public Plan(String name, List<Day> days) {
        this.name = name;
        this.days = days;
    }

    public Plan(int id, String name, List<Day> day) {
        this.id = id;
        this.name = name;
        this.days = day;
    }

    public Plan() {
    }

    public Plan(String name) {
        this.name = name;
    }

    public Plan(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Day> getDays() {
        return days;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", days=" + days +
                '}';
    }

    public void setDays(List<Day> day) {
        this.days = day;
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
