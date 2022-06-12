package com.ssg.task.api.controller;

import com.google.gson.Gson;
import com.ssg.task.api.dto.ItemDto;
import com.ssg.task.api.dto.request.ItemRequestDto;
import com.ssg.task.api.service.ItemService;
import com.ssg.task.api.service.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
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
@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ItemServiceImpl itemService;

    @Test
    public void 아이템추가요청() throws Exception{
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setName("아이템");
        Gson gson = new Gson();
        String content = gson.toJson(itemRequestDto);

        mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isCreated());

        verify(itemService).addItem(itemRequestDto);

    }

    @Test
    public void 아이템삭제요청() throws Exception{
        mockMvc.perform(delete("/api/v1/items/itemname"))
                .andExpect(status().isOk());

        verify(itemService).deleteItem("itemname");

    }


    @Test
    public void 유저구매가능상품요청() throws Exception{
        List<ItemDto> itemList = new ArrayList<>();
        ItemDto itemDto = new ItemDto("item1",10000);
        itemList.add(itemDto);

        given(itemService.findPurchaseItem(any())).willReturn(itemList);

        mockMvc.perform(get("/api/v1/items/purchase/kenny"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("item1")));
    }


}