package com.example.pulse.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class PulseException extends RuntimeException{
    public PulseException(){}
    int errorCode;
    public PulseException(int errorCode){
        this.errorCode = errorCode;
    }
}
