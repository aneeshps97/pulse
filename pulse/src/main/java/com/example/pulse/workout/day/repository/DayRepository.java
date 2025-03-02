package com.example.pulse.workout.day.repository;

import com.example.pulse.workout.day.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<Day,Integer> {
}
