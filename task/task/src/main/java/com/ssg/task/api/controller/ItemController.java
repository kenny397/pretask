package com.ssg.task.api.controller;

import com.ssg.task.api.dto.request.ItemRequestDto;
import com.ssg.task.api.dto.response.BaseResponseBody;
import com.ssg.task.api.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/")
    public ResponseEntity<BaseResponseBody> addItem(@RequestBody ItemRequestDto itemRequestDto){
        itemService.addItem(itemRequestDto);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,itemRequestDto+"님이 삭제에 성공하셨습니다"));
    }
}
