package com.example.pulse.Response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response<T> {
    int statusCode, status;
    String message;
    T data;

    public Response() {
    }

    public Response(int statusCode, int status, String message, T data) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
