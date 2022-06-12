package com.ssg.task.api.controller;

import com.ssg.task.api.dto.request.UserRequestDto;
import com.ssg.task.api.dto.response.BaseResponseBody;
import com.ssg.task.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponseBody> signUp(@RequestBody UserRequestDto userRequestDto){
        String name = userService.signUp(userRequestDto);
        return ResponseEntity.status(201).body(BaseResponseBody.of(201,name+"님이 회원가입에 성공하셨습니다"));
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<BaseResponseBody> deleteUser(@PathVariable("userName") String userName){
        userService.deleteUser(userName);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,userName+"님이 삭제에 성공하셨습니다"));
    }

    @PatchMapping("/withdraw/{userName}")
    public ResponseEntity<BaseResponseBody> withdraw(@PathVariable("userName") String userName){
        userService.resign(userName);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,userName+"님이 탈퇴에 성공하셨습니다"));
    }


}
