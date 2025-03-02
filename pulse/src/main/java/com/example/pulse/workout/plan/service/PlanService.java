package com.example.pulse.workout.plan.service;

import com.example.pulse.workout.plan.entity.Plan;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PlanService {
    public Plan addPlan(Plan plan);
    public Plan updatePlan(int id,Plan plan);
    public Optional<Plan> findPlanById(int id);
    public boolean deletePlan(int id);
}
