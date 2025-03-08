package com.example.pulse.Response;

import com.example.pulse.cacheLoader.statusCode.service.StatusCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GenerateResponse {
    StatusCodeService statusCodeService;
    @Autowired
    public GenerateResponse(StatusCodeService statusCodeService){
        this.statusCodeService = statusCodeService;
    }
    public  <T>ResponseEntity<Response<T>> formatResponse(int statusCode, int Status, T data, HttpStatus httpStatusCode){
        Response<T> response = new Response<>();
        response.setStatus(Status);
        response.setStatusCode(statusCode);
        response.setMessage(statusCodeService.getMessageForCode(statusCode));
        response.setData(data);
        return (new ResponseEntity<>(response,httpStatusCode));
    }
}
