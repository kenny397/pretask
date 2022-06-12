package com.ssg.task.api.exception;

public class ExistException extends RuntimeException {
    public ExistException(String userName){
        super(userName+"가 존재합니다.");
    }
}
