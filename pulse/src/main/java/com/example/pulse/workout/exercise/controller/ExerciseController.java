package com.example.pulse.workout.exercise.controller;
import com.example.pulse.Response.GenerateResponse;
import com.example.pulse.Response.Response;
import com.example.pulse.constants.StatusCodes;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*controller layer to add, modify, fetch and remove exercise details
Requests are provided as postman collection along with this controller*/

@RestController
@RequestMapping("/exercise")
@AllArgsConstructor
public class ExerciseController {
    ExerciseService exerciseService;
    GenerateResponse generateResponse;
    public static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);

    @PostMapping
    public ResponseEntity<Response<Exercise>> add(@RequestBody  Exercise exercise) throws Exception {
        logger.info("Request received for adding exercise::{}",exercise.toString());
        exercise = exerciseService.add(exercise);
        return generateResponse.formatResponse(StatusCodes.EXERCISE_ADDED_SUCCESSFULLY, StatusCodes.SUCCESS,exercise,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<Response<Exercise>> findExerciseById(@PathVariable int exerciseId){
        logger.info("Request received for fetching exercise id::{}",exerciseId);
        Exercise exercise = exerciseService.findById(exerciseId);
        return generateResponse.formatResponse(StatusCodes.EXERCISE_FETCHED_SUCCESSFULLY, StatusCodes.SUCCESS,exercise,HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Response<List<Exercise>>> findAll(){
        logger.info("Request received for finding all exercises");
        List<Exercise> exercises= exerciseService.findAll();
        return generateResponse.formatResponse(StatusCodes.EXERCISE_FETCHED_SUCCESSFULLY, StatusCodes.SUCCESS,exercises,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<Response<Exercise>> updateExerciseDetails(@PathVariable int exerciseId, @RequestBody Exercise exercise){
        logger.info("Request received for updating exercise:: id:: {}  data::{}",exerciseId,exercise.toString());
        exercise = exerciseService.update(exerciseId,exercise);
        return generateResponse.formatResponse(StatusCodes.EXERCISE_UPDATED_SUCCESSFULLY, StatusCodes.SUCCESS,exercise,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Response<Boolean>> deleteExercise(@PathVariable int exerciseId){
        logger.info("Request received for deleting exercise id::{}",exerciseId);
        boolean isDeletionSuccess = exerciseService.delete(exerciseId);
        return generateResponse.formatResponse(StatusCodes.EXERCISE_DELETED_SUCCESSFULLY, StatusCodes.SUCCESS,isDeletionSuccess,HttpStatus.ACCEPTED);
    }
}
