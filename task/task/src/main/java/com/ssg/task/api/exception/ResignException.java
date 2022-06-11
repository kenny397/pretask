package com.ssg.task.api.exception;

public class ResignException extends RuntimeException {
    public ResignException(String userName){
        super(userName+"는 탈퇴한 회원입니다.");
    }
}