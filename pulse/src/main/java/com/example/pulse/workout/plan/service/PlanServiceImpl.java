package com.example.pulse.workout.plan.service;

import com.example.pulse.workout.plan.entity.Plan;
import com.example.pulse.workout.plan.repository.PlanRespository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService{
    PlanRespository planRespository;
    PlanServiceImpl(PlanRespository planRespository){
        this.planRespository = planRespository;
    }
    @Override
    public Plan addPlan(Plan plan) {
        return planRespository.save(plan);
    }

    @Override
    public Plan updatePlan(int id,Plan plan) {
        plan.setId(id);
        return planRespository.save(plan);
    }

    @Override
    public Optional<Plan> findPlanById(int id) {
        return planRespository.findById(id);
    }

    @Override
    public boolean deletePlan(int id) {
        Optional<Plan> plan =planRespository.findById(id);
        plan.ifPresent(value -> planRespository.delete(value));
        return !planRespository.existsById(id);
    }
}
