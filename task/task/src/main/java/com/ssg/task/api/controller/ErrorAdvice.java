package com.ssg.task.api.controller;

import com.ssg.task.api.dto.response.BaseResponseBody;
import com.ssg.task.api.exception.ExistException;
import com.ssg.task.api.exception.NameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NameNotFoundException.class)
    public ResponseEntity<BaseResponseBody> userNotFound(){
        return ResponseEntity.status(400).body(BaseResponseBody.of(400,"존재하지 않는 이름입니다."));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExistException.class)
    public ResponseEntity<BaseResponseBody> exist(){
        return ResponseEntity.status(400).body(BaseResponseBody.of(400,"중복된 이름입니다."));
    }
}
