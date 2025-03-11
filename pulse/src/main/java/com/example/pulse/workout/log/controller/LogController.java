package com.example.pulse.workout.log.controller;

import com.example.pulse.Response.GenerateResponse;
import com.example.pulse.Response.Response;
import com.example.pulse.constants.StatusCodes;
import com.example.pulse.workout.log.entity.Log;
import com.example.pulse.workout.log.service.LogService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/log")
@AllArgsConstructor
public class LogController {
    LogService logService;
    GenerateResponse generateResponse;
    public static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @PostMapping
    public ResponseEntity<Response<Log>> addLog(@RequestBody Log log){
        logger.info("Reqeust received for adding log ::{}",log.toString());
        log =logService.add(log);
        return generateResponse.formatResponse(StatusCodes.LOG_ADDED_SUCCESSFULLY,StatusCodes.SUCCESS,log, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Log>> updateLog(@PathVariable int id, @RequestBody Log log){
        logger.info("Request received for updating log id::{} data::{}",id,log.toString());
        log = logService.update(id,log);
        return generateResponse.formatResponse(StatusCodes.LOG_UPDATED_SUCCESSFULLY,StatusCodes.SUCCESS,log,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Log>> findLogById(@PathVariable int id){
        logger.info("Request received for finding log by id::{}",id);
        Log log = logService.findById(id);
        return generateResponse.formatResponse(StatusCodes.LOG_FETCHING_SUCCESS,StatusCodes.SUCCESS,log,HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Response<List<Log>>> findAll(){
        logger.info("Request received for finding all the logs");
        List<Log> logs = logService.findAll();
        return generateResponse.formatResponse(StatusCodes.LOG_FETCHING_SUCCESS,StatusCodes.SUCCESS,logs,HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> deleteLog(@PathVariable int id){
        logger.info("Request received for deleting log id ::{}",id);
        boolean isLogDeletionSuccess = logService.delete(id);
        return generateResponse.formatResponse(StatusCodes.LOG_DELETION_SUCCESS,StatusCodes.SUCCESS,isLogDeletionSuccess,HttpStatus.ACCEPTED);
    }
}
