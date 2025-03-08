package com.example.pulse.workout.exercise.entity;

import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.log.entity.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "exercise")
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "comment")
    private String comment;
}
