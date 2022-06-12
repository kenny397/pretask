package com.ssg.task.api.controller;

import com.google.gson.Gson;
import com.ssg.task.api.db.entity.Promotion;
import com.ssg.task.api.dto.ItemDto;
import com.ssg.task.api.dto.request.PromotionItemsRequestDto;
import com.ssg.task.api.dto.request.PromotionRequestDto;
import com.ssg.task.api.dto.request.UserRequestDto;
import com.ssg.task.api.dto.response.ItemPromotionResponseDto;
import com.ssg.task.api.service.PromotionServiceImpl;
import com.ssg.task.api.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PromotionController.class)
class PromotionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PromotionServiceImpl promotionService;

    @Test
    public void 프로모션추가요청() throws Exception{
        PromotionRequestDto promotionRequestDto = new PromotionRequestDto();
        promotionRequestDto.setName("promotion");
        Gson gson = new Gson();
        String content = gson.toJson(promotionRequestDto);

        mockMvc.perform(post("/api/v1/promotions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated());

        verify(promotionService).addPromotion(promotionRequestDto);

    }

    @Test
    public void 프로모션삭제요청() throws Exception{
        mockMvc.perform(delete("/api/v1/promotions/promotionname"))
                .andExpect(status().isOk());

        verify(promotionService).deletePromotion("promotionname");
    }

    @Test
    public void 프로모션아이템추가요청() throws Exception{
        PromotionItemsRequestDto promotionItemsRequestDto = new PromotionItemsRequestDto();
        promotionItemsRequestDto.setPromotion("promotion");
        promotionItemsRequestDto.setItems("item");
        Gson gson = new Gson();
        String content = gson.toJson(promotionItemsRequestDto);

        mockMvc.perform(post("/api/v1/promotions/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated());

        verify(promotionService).addItemsToPromotion(promotionItemsRequestDto);
    }

    @Test
    public void 유저구매가능상품요청() throws Exception{
        ItemPromotionResponseDto itemPromotionResponseDto = new ItemPromotionResponseDto();
        itemPromotionResponseDto.setPromotionName("promotion");


        given(promotionService.findPromotion(any())).willReturn(itemPromotionResponseDto);

        mockMvc.perform(get("/api/v1/promotions/kenny"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("promotion")));
    }
}