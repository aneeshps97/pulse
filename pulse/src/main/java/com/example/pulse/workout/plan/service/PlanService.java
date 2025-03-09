package com.example.pulse.workout.plan.service;

import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.plan.entity.Plan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PlanService {
    public Plan addPlan(Plan plan) throws PulseException;
    public Plan updatePlan(int id,Plan plan) throws PulseException;
    public Plan findPlanById(int id) throws PulseException;
    public List<Plan> findAll() throws PulseException;
    public boolean deletePlan(int id) throws PulseException;
}
