package com.example.pulse.controllerAdvice;


import com.example.pulse.Response.Response;
import com.example.pulse.cacheLoader.statusCode.service.StatusCodeService;
import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    StatusCodeService statusCodeService;
    @Autowired
    public ControllerAdvice(StatusCodeService statusCodeService){
      this.statusCodeService = statusCodeService;
    }
    @ExceptionHandler
    public <T> ResponseEntity<Response<T>>handleException(PulseException e){
        Response<T> response = new Response<>();
        response.setStatusCode(e.getErrorCode());
        response.setStatus(StatusCodes.FAIL);
        response.setMessage(statusCodeService.getMessageForCode(e.getErrorCode()));
        return new ResponseEntity<Response<T>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<Response<T>> handleGeneralException(Exception e) {
       // logger.error("Unhandled exception: {}", e.getMessage(), e);
        Response<T> response = new Response<>();
        response.setStatusCode(StatusCodes.GENERAL_EXCEPTION);
        response.setStatus(StatusCodes.FAIL);
        response.setMessage(statusCodeService.getMessageForCode(StatusCodes.GENERAL_EXCEPTION));
        System.out.println(""+e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<Response<T>>(response, HttpStatus.BAD_REQUEST);
    }
}
