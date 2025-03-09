package com.example.pulse.workout.categories.entity;

import com.example.pulse.workout.exercise.entity.Exercise;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "Category_of_exercise")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "category")
    List<Exercise> exercises;
}
