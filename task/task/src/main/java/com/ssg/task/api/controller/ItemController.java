package com.ssg.task.api.controller;

import com.ssg.task.api.dto.ItemDto;
import com.ssg.task.api.dto.PromotionDto;
import com.ssg.task.api.dto.request.ItemRequestDto;
import com.ssg.task.api.dto.response.BaseResponseBody;
import com.ssg.task.api.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping("")
    public ResponseEntity<BaseResponseBody> addItem(@RequestBody ItemRequestDto itemRequestDto){
        itemService.addItem(itemRequestDto);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,itemRequestDto.getName()+" 아이템추가에 성공하셨습니다"));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<BaseResponseBody> deleteItem(@PathVariable("name") String itemName){
        itemService.deleteItem(itemName);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,itemName+"아이템이 삭제되었습니다"));
    }

    @GetMapping("/purchase/{userName}")
    public ResponseEntity<List<ItemDto>> isPurchaseItem(@PathVariable("userName")String userName){
        List<ItemDto> itemList= itemService.isPurchasItem(userName);
        return ResponseEntity.status(200).body(itemList);
    }




}
