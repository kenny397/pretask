package com.ssg.task.api.exception;

public class ExistException extends RuntimeException {
    public ExistException(String userName){
        super(userName+"유저가 존재하지 않습니다.");
    }
}
