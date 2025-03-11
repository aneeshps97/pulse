package com.example.pulse.controllerAdvice;


import com.example.pulse.Response.Response;
import com.example.pulse.cacheLoader.statusCode.service.StatusCodeService;
import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
@AllArgsConstructor
public class ControllerAdvice {
    StatusCodeService statusCodeService;
    public static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);
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
        logger.error("Unhandled exception: {}", e.getMessage(), e);
        Response<T> response = new Response<>();
        response.setStatusCode(StatusCodes.GENERAL_EXCEPTION);
        response.setStatus(StatusCodes.FAIL);
        response.setMessage(statusCodeService.getMessageForCode(StatusCodes.GENERAL_EXCEPTION));
        e.printStackTrace();
        return new ResponseEntity<Response<T>>(response, HttpStatus.BAD_REQUEST);
    }
}
