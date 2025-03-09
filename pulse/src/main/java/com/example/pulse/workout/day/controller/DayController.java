package com.example.pulse.workout.day.controller;

import com.example.pulse.Response.GenerateResponse;
import com.example.pulse.Response.Response;
import com.example.pulse.constants.StatusCodes;
import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.day.service.DayService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/day")
public class DayController {
    DayService dayService;
    GenerateResponse generateResponse;
    static final Logger logger = LoggerFactory.getLogger(DayController.class);

    @PostMapping
    public ResponseEntity<Response<Day>> addDay(@RequestBody Day day){
        logger.info("Request received for adding day::{}",day);
        day = dayService.add(day);
        return generateResponse.formatResponse(StatusCodes.DAY_ADDED_SUCCESSFULLY,StatusCodes.SUCCESS, day,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Day>> updateDay(@PathVariable int id, @RequestBody Day day){
        logger.info("Request received for updating day::id ::{} :: data::{}",id,day);
        day= dayService.update(id,day);
        return generateResponse.formatResponse(StatusCodes.DAY_UPDATED_SUCCESSFULLY,StatusCodes.SUCCESS, day,HttpStatus.ACCEPTED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Day>> findDayById(@PathVariable int id){
        logger.info("Request received for finding day  id::{}",id);
        Day day = dayService.findById(id);
        return generateResponse.formatResponse(StatusCodes.DAY_FETCHING_SUCCESS,StatusCodes.SUCCESS, day,HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Response<List<Day>>> findAll(){
        logger.info("Request received for finding all days");
        List<Day> days = dayService.findAll();
        return generateResponse.formatResponse(StatusCodes.DAY_FETCHING_SUCCESS,StatusCodes.SUCCESS, days,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> deleteDay(@PathVariable int id){
        logger.info("Request received for deleting day id ::{}",id);
        boolean isDayDeletionSuccess = false;
        isDayDeletionSuccess = dayService.delete(id);
        return generateResponse.formatResponse(StatusCodes.DAY_DELETION_SUCCESS,StatusCodes.SUCCESS,isDayDeletionSuccess,HttpStatus.ACCEPTED);
    }
}
