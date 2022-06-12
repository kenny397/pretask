package com.ssg.task.api.service;

import com.ssg.task.api.db.entity.*;
import com.ssg.task.api.db.repository.ItemPromotionRepository;
import com.ssg.task.api.db.repository.ItemRepository;
import com.ssg.task.api.db.repository.UserRepository;
import com.ssg.task.api.dto.request.ItemRequestDto;
import com.ssg.task.api.dto.request.PromotionRequestDto;
import com.ssg.task.api.dto.request.UserRequestDto;
import com.ssg.task.api.exception.ExistException;
import com.ssg.task.api.exception.NameNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;


    @Test
    public void 프로모션_생성(){
        ItemRequestDto itemRequestDto=createItemRequest();
        Item item = createItem(itemRequestDto);


        given(itemRepository.save(any())).willReturn(item);
        String newItemName= itemService.addItem(itemRequestDto);

        given(itemRepository.findByName(itemRequestDto.getName())).willReturn(Optional.ofNullable(item));
        Item findItem = itemRepository.findByName(newItemName).get();

        assertThat(item.getId()).isEqualTo(findItem.getId());
        assertThat(item.getName()).isEqualTo(findItem.getName());

    }

    @Test
    public void 프로모션_생성_중복_프로모션_예외() {
        ItemRequestDto itemRequestDto=createItemRequest();
        Item item = createItem(itemRequestDto);

        given(itemRepository.findByName(itemRequestDto.getName())).willReturn(Optional.ofNullable(item));

        ExistException existException = assertThrows(ExistException.class, () -> itemService.addItem(itemRequestDto));
        assertThat(itemRequestDto.getName()+"가 존재합니다.").isEqualTo(existException.getMessage());
    }

    @Test
    public void 프로모션_삭제(){
        ItemRequestDto itemRequestDto=createItemRequest();
        Item item = createItem(itemRequestDto);


        given(itemRepository.findByName(itemRequestDto.getName())).willReturn(Optional.ofNullable(item));
        Optional<Item> deleteItemBefore = itemRepository.findByName(itemRequestDto.getName());
        assertThat(deleteItemBefore.get()).isEqualTo(item);

        itemService.deleteItem(itemRequestDto.getName());

        given(itemRepository.findByName(itemRequestDto.getName())).willReturn(Optional.ofNullable(null));

        Optional<Item> deleteItemAfter = itemRepository.findByName(itemRequestDto.getName());
        assertThat(deleteItemAfter).isEqualTo(Optional.ofNullable(null));
    }

    @Test
    public void 프로모션_삭제_존재하지_않은_프로모션_예외() {
        ItemRequestDto itemRequestDto=createItemRequest();
        Item item = createItem(itemRequestDto);

        given(itemRepository.findByName(itemRequestDto.getName())).willReturn(Optional.ofNullable(null));

        NameNotFoundException nameNotFoundException = assertThrows(NameNotFoundException.class, () -> itemService.deleteItem(itemRequestDto.getName()));
        assertThat(itemRequestDto.getName()+"가 존재하지 않습니다.").isEqualTo(nameNotFoundException.getMessage());
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
        item.setDisplayStartDate(itemService.strToDate(itemRequestDto.getDisplayStart()));
        item.setDisplayEndDate(itemService.strToDate(itemRequestDto.getDisplayEnd()));
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
        promotion.setPromotionStart(itemService.strToDate(promotionRequestDto.getDisplayStart()));
        promotion.setPromotionEnd(itemService.strToDate(promotionRequestDto.getDisplayEnd()));
        promotion.setDiscountAmount(promotionRequestDto.getDiscountAmount());
        return promotion;
    }
}