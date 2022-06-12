package com.ssg.task.api.service;

import com.ssg.task.api.db.entity.*;
import com.ssg.task.api.db.repository.ItemPromotionRepository;
import com.ssg.task.api.db.repository.ItemRepository;
import com.ssg.task.api.db.repository.PromotionRepository;
import com.ssg.task.api.db.repository.UserRepository;
import com.ssg.task.api.dto.PromotionSearchCondition;
import com.ssg.task.api.dto.request.ItemRequestDto;
import com.ssg.task.api.dto.request.PromotionItemsRequestDto;
import com.ssg.task.api.dto.request.PromotionRequestDto;
import com.ssg.task.api.dto.request.UserRequestDto;
import com.ssg.task.api.dto.response.DiscountType;
import com.ssg.task.api.dto.response.ItemPromotionResponseDto;
import com.ssg.task.api.exception.ExistException;
import com.ssg.task.api.exception.NameNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PromotionServiceTest {
    @InjectMocks
    private PromotionServiceImpl promotionService;

    @Mock
    private PromotionRepository promotionRepository;
    @Mock
    private ItemPromotionRepository itemPromotionRepository;
    @Mock
    private ItemRepository itemRepository;

    @Test
    public void 프로모션_생성(){
        PromotionRequestDto promotionRequestDto=createPromotionRequest();
        Promotion promotion = createPromotion(promotionRequestDto);


        given(promotionRepository.save(any())).willReturn(promotion);
        String newPromotionName= promotionService.addPromotion(promotionRequestDto);

        given(promotionRepository.findByName(promotionRequestDto.getName())).willReturn(Optional.ofNullable(promotion));
        Promotion findPromotion = promotionRepository.findByName(newPromotionName).get();

        assertThat(promotion.getId()).isEqualTo(findPromotion.getId());
        assertThat(promotion.getName()).isEqualTo(findPromotion.getName());

    }

    @Test
    public void 프로모션_생성_중복_프로모션_예외() {
        PromotionRequestDto promotionRequestDto=createPromotionRequest();
        Promotion promotion = createPromotion(promotionRequestDto);

        given(promotionRepository.findByName(promotionRequestDto.getName())).willReturn(Optional.ofNullable(promotion));

        ExistException existException = assertThrows(ExistException.class, () -> promotionService.addPromotion(promotionRequestDto));
        assertThat(promotionRequestDto.getName()+"가 존재합니다.").isEqualTo(existException.getMessage());
    }

    @Test
    public void 프로모션_삭제(){
        PromotionRequestDto promotionRequestDto=createPromotionRequest();
        Promotion promotion = createPromotion(promotionRequestDto);


        given(promotionRepository.findByName(promotionRequestDto.getName())).willReturn(Optional.ofNullable(promotion));
        Optional<Promotion> deletePromotionBefore = promotionRepository.findByName(promotionRequestDto.getName());
        assertThat(deletePromotionBefore.get()).isEqualTo(promotion);

        promotionService.deletePromotion(promotionRequestDto.getName());

        given(promotionRepository.findByName(promotionRequestDto.getName())).willReturn(Optional.ofNullable(null));

        Optional<Promotion> deletePromotionAfter = promotionRepository.findByName(promotionRequestDto.getName());
        assertThat(deletePromotionAfter).isEqualTo(Optional.ofNullable(null));
    }

    @Test
    public void 프로모션_삭제_존재하지_않은_프로모션_예외() {
        PromotionRequestDto promotionRequestDto=createPromotionRequest();
        Promotion promotion = createPromotion(promotionRequestDto);

        given(promotionRepository.findByName(promotionRequestDto.getName())).willReturn(Optional.ofNullable(null));

        NameNotFoundException nameNotFoundException = assertThrows(NameNotFoundException.class, () -> promotionService.deletePromotion(promotionRequestDto.getName()));
        assertThat(promotionRequestDto.getName()+"가 존재하지 않습니다.").isEqualTo(nameNotFoundException.getMessage());
    }

    @Test
    public void 프로모션_아이템_추가(){
        ItemRequestDto itemRequest = createItemRequest();
        PromotionRequestDto promotionRequest = createPromotionRequest();
        Item item = createItem(itemRequest);
        Promotion promotion = createPromotion(promotionRequest);
        ItemPromotion itemPromotion = new ItemPromotion();
        itemPromotion.setItem(item);
        itemPromotion.setPromotion(promotion);
        PromotionItemsRequestDto promotionItemsRequestDto = new PromotionItemsRequestDto();
        promotionItemsRequestDto.setPromotion(promotion.getName());
        promotionItemsRequestDto.setItems(item.getName());
        given(promotionRepository.findByName(promotion.getName())).willReturn(Optional.ofNullable(promotion));
        given(itemRepository.findByName(item.getName())).willReturn(Optional.ofNullable(item));
        given(itemPromotionRepository.save(any())).willReturn(itemPromotion);

        promotionService.addItemsToPromotion(promotionItemsRequestDto);

        given(itemPromotionRepository.findByItem(item)).willReturn(Optional.ofNullable(itemPromotion));
        ItemPromotion findItemPromotion = itemPromotionRepository.findByItem(item).get();

        assertThat(findItemPromotion).isEqualTo(itemPromotion);

    }

    @Test
    public void 상품내에있는_프로모션정보반환(){
        ItemRequestDto itemRequest = createItemRequest();
        PromotionRequestDto promotionRequest = createPromotionRequest();
        Item item = createItem(itemRequest);
        Promotion promotion = createPromotion(promotionRequest);
        ItemPromotion itemPromotion = new ItemPromotion();
        itemPromotion.setItem(item);
        itemPromotion.setPromotion(promotion);
        PromotionSearchCondition searchCondition = new PromotionSearchCondition();
        searchCondition.setItemName(item.getName());
        List<ItemPromotion> findItemPromotion=new ArrayList<>();
        findItemPromotion.add(itemPromotion);

        given(itemRepository.findByName(item.getName())).willReturn(Optional.ofNullable(item));
        given(itemPromotionRepository.findPromotion(searchCondition)).willReturn(findItemPromotion);
        ItemPromotionResponseDto itemPromotionResponseDto = promotionService.findPromotion(item.getName());
        assertThat(itemPromotionResponseDto.getPrice()).isEqualTo(item.getPrice());
        assertThat(itemPromotionResponseDto.getDiscountPrice()).isEqualTo(9000);
        assertThat(itemPromotionResponseDto.getDiscountType()).isEqualTo(DiscountType.AMOUNT);

    }

   public ItemRequestDto createItemRequest(){
        ItemRequestDto itemRequestDto=new ItemRequestDto();
        itemRequestDto.setName("아이템1");
        itemRequestDto.setPrice(10000);
        itemRequestDto.setType(0L);
        itemRequestDto.setDisplayStart("2022-04-30");
        itemRequestDto.setDisplayEnd("2023-01-01");
        return itemRequestDto;
    }

    public Item createItem(ItemRequestDto itemRequestDto){
        Item item = new Item();
        item.setName(itemRequestDto.getName());
        item.setDisplayStartDate(promotionService.strToDate(itemRequestDto.getDisplayStart()));
        item.setDisplayEndDate(promotionService.strToDate(itemRequestDto.getDisplayEnd()));
        item.setPrice(itemRequestDto.getPrice());
        return item;
    }
    public PromotionRequestDto createPromotionRequest(){
        PromotionRequestDto promotionRequestDto=new PromotionRequestDto();
        promotionRequestDto.setName("프로모션1");
        promotionRequestDto.setDiscountAmount(1000);
        promotionRequestDto.setDisplayStart("2022-04-30");
        promotionRequestDto.setDisplayEnd("2023-01-01");
        return promotionRequestDto;
    }

    public Promotion createPromotion(PromotionRequestDto promotionRequestDto){
        Promotion promotion = new Promotion();
        promotion.setName(promotionRequestDto.getName());
        promotion.setPromotionStart(promotionService.strToDate(promotionRequestDto.getDisplayStart()));
        promotion.setPromotionEnd(promotionService.strToDate(promotionRequestDto.getDisplayEnd()));
        promotion.setDiscountAmount(promotionRequestDto.getDiscountAmount());
        return promotion;
    }
}