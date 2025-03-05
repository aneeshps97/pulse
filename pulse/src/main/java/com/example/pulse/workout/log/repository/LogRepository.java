package com.example.pulse.workout.log.repository;

import com.example.pulse.workout.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log,Integer> {
}
