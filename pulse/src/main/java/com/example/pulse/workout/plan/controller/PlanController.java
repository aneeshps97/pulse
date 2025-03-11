package com.example.pulse.workout.plan.controller;

import com.example.pulse.Response.GenerateResponse;
import com.example.pulse.Response.Response;
import com.example.pulse.constants.StatusCodes;
import com.example.pulse.workout.plan.entity.Plan;
import com.example.pulse.workout.plan.service.PlanService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plan")
@AllArgsConstructor
public class PlanController {
    PlanService planService;
    GenerateResponse generateResponse;
    public static final Logger logger = LoggerFactory.getLogger(PlanController.class);

    @PostMapping
    public ResponseEntity<Response<Plan>> addPlan(@RequestBody Plan plan){
        logger.info("Request received for adding new plan ::{}",plan.toString());
        plan = planService.addPlan(plan);
        return generateResponse.formatResponse(StatusCodes.PLAN_ADDED_SUCCESSFULLY,StatusCodes.SUCCESS,plan, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Plan>> findPlanById(@PathVariable int id){
        logger.info("Request received for finding plan by id ::{}",id);
        Plan plan = planService.findPlanById(id);
        return generateResponse.formatResponse(StatusCodes.PLAN_FETCHED_SUCCESSFULLY,StatusCodes.SUCCESS,plan, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Response<List<Plan>>> findAll(){
        logger.info("Reqeust received for finding all the plans");
        List<Plan> plans = planService.findAll();
        return generateResponse.formatResponse(StatusCodes.PLAN_FETCHED_SUCCESSFULLY,StatusCodes.SUCCESS,plans, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Plan>> updatePlan(@PathVariable int id,@RequestBody Plan plan){
        logger.info("Request received for updating plans:: id ::{} data::{}",id,plan.toString());
        plan = planService.updatePlan(id,plan);
        return generateResponse.formatResponse(StatusCodes.PLAN_UPDATED_SUCCESSFULLY,StatusCodes.SUCCESS,plan, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> deletePlan(@PathVariable int id){
        logger.info("Request received for deleting plan with id ::{}",id);
        boolean isPlanDeletionSuccess = planService.deletePlan(id);
        return generateResponse.formatResponse(StatusCodes.PLAN_DELETED_SUCCESSFULLY,StatusCodes.SUCCESS,isPlanDeletionSuccess, HttpStatus.ACCEPTED);

    }



}
