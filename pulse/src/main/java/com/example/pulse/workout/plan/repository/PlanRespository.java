package com.example.pulse.workout.plan.repository;

import com.example.pulse.workout.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRespository extends JpaRepository<Plan, Integer> {
}
