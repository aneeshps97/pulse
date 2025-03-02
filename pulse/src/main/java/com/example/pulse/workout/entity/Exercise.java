package com.example.pulse.workout.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Exercise() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
