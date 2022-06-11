package com.ssg.task.api.exception;

public class NameNotFoundException extends RuntimeException{
    public NameNotFoundException(String userName){
        super(userName+"가 존재하지 않습니다.");
    }
}
