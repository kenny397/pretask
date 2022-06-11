package com.ssg.task.api.controller;

import com.ssg.task.api.dto.request.PromotionItemsRequestDto;
import com.ssg.task.api.dto.request.PromotionRequestDto;
import com.ssg.task.api.dto.response.BaseResponseBody;
import com.ssg.task.api.dto.response.ItemPromotionResponseDto;
import com.ssg.task.api.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
@Slf4j
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("")
    public ResponseEntity<BaseResponseBody> addPromotion(@RequestBody PromotionRequestDto promotionRequestDto){
        promotionService.addPromotion(promotionRequestDto);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,promotionRequestDto.getName()+"님이 프로모션 추가에 성공하셨습니다"));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<BaseResponseBody> deletePromotion(@PathVariable("name") String promotionName){
        promotionService.deletePromotion(promotionName);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,promotionName+"프로모션이 삭제되었습니다"));
    }

    @PostMapping("/items")
    public ResponseEntity<BaseResponseBody> setPromotionItems(@RequestBody PromotionItemsRequestDto promotionItemsRequestDto){
        promotionService.addItemsToPromotion(promotionItemsRequestDto);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,promotionItemsRequestDto.getPromotion()+"프로모션에 "+promotionItemsRequestDto.getItems()+"상품이 추가되었습니다"));
    }

    @GetMapping("/{itemName}")
    public ResponseEntity<ItemPromotionResponseDto> selectPromotion(@PathVariable("itemName")String itemName){
        ItemPromotionResponseDto itemPromotionResponseDto = promotionService.findPromotion(itemName);
        return ResponseEntity.status(200).body(itemPromotionResponseDto);
    }

}
