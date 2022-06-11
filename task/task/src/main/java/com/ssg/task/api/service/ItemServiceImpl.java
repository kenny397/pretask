package com.ssg.task.api.service;

import com.ssg.task.api.db.entity.Item;
import com.ssg.task.api.db.entity.Type;
import com.ssg.task.api.db.entity.User;
import com.ssg.task.api.db.entity.UserStatus;
import com.ssg.task.api.db.repository.ItemRepository;
import com.ssg.task.api.db.repository.UserRepository;
import com.ssg.task.api.dto.ItemDto;
import com.ssg.task.api.dto.ItemSearchCondition;
import com.ssg.task.api.dto.request.ItemRequestDto;
import com.ssg.task.api.exception.ExistException;
import com.ssg.task.api.exception.NameNotFoundException;
import com.ssg.task.api.exception.ResignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public String addItem(ItemRequestDto itemRequestDto) {
        Optional<Item> itemOptional = itemRepository.findByName(itemRequestDto.getName());
        if(itemOptional.isPresent())throw new ExistException(itemRequestDto.getName());
        Item item =new Item();
        item.setName(itemRequestDto.getName());
        item.setPrice(itemRequestDto.getPrice());
        if(itemRequestDto.getType()==1)item.setType(Type.EE);
        else item.setType(Type.NORMAL);
        item.setDisplayStartDate(strToDate(itemRequestDto.getDisplayStart()));
        item.setDisplayEndDate(strToDate(itemRequestDto.getDisplayEnd()));
        Item saveItem = itemRepository.save(item);
        return saveItem.getName();
    }

    @Transactional
    @Override
    public void deleteItem(String itemName) {
        Optional<Item> itemOptional = itemRepository.findByName(itemName);
        itemOptional.orElseThrow(()->new NameNotFoundException(itemName));
        itemRepository.delete(itemOptional.get());
    }

    @Override
    public List<ItemDto> isPurchasItem(String userName) {
        //탈퇴한 회원 처리
        Optional<User> userOptional = userRepository.findByName(userName);
        userOptional.orElseThrow(() -> new NameNotFoundException(userName));
        User user = userOptional.get();
        if(user.getStatus()== UserStatus.RESIGN){
            throw new ResignException(userName);
        }else{
            ItemSearchCondition itemSearchCondition=new ItemSearchCondition();
            itemSearchCondition.setUserName(userName);
            itemSearchCondition.setUserType(user.getType());
            List<ItemDto> purchase = itemRepository.isPurchase(itemSearchCondition);
            return purchase;
        }


    }


    public LocalDateTime strToDate(String input){
        String[]dates=input.split("-");
        LocalDateTime localDateTime =LocalDateTime.of(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]),0,0);
        return localDateTime;
    }
}
