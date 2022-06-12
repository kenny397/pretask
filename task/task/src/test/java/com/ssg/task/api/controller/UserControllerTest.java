package com.ssg.task.api.controller;

import com.google.gson.Gson;
import com.ssg.task.api.dto.request.ItemRequestDto;
import com.ssg.task.api.dto.request.UserRequestDto;
import com.ssg.task.api.service.ItemServiceImpl;
import com.ssg.task.api.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @Test
    public void 유저추가요청() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("user");
        Gson gson = new Gson();
        String content = gson.toJson(userRequestDto);

        mockMvc.perform(post("/api/v1/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated());


    }

    @Test
    public void 유저삭제요청() throws Exception{
        mockMvc.perform(delete("/api/v1/users/username"))
                .andExpect(status().isOk());

        verify(userService).deleteUser("username");
    }

    @Test
    public void 유저탈퇴요청() throws Exception{
        mockMvc.perform(patch("/api/v1/users/withdraw/username"))
                .andExpect(status().isOk());

        verify(userService).resign("username");
    }
}