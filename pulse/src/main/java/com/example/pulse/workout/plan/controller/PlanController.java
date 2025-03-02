package com.example.pulse.workout.plan.controller;

import com.example.pulse.workout.plan.entity.Plan;
import com.example.pulse.workout.plan.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/plan")
public class PlanController {
    PlanService planService;
    PlanController(PlanService planService){
        this.planService = planService;
    }
    @PostMapping
    public Plan addPlan(@RequestBody Plan plan){
        return planService.addPlan(plan);
    }

    @GetMapping("/{id}")
    public Optional<Plan> findPlanById(@PathVariable int id){
        return planService.findPlanById(id);
    }

    @PutMapping("/{id}")
    public Plan updatePlan(@PathVariable int id,@RequestBody Plan plan){
        return planService.updatePlan(id,plan);
    }
    @DeleteMapping("/{id}")
    public boolean deletePlan(@PathVariable int id){
        return planService.deletePlan(id);
    }



}
