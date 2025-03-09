package com.example.pulse.workout.categories.repository;

import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.categories.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
