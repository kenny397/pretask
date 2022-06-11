package com.ssg.task.api.service;


import com.ssg.task.api.dto.request.UserRequestDto;

public interface UserService {
    public String signUp(UserRequestDto userRequestDto);
    public void resign(String userName);
    public void deleteUser(String userName);
}
