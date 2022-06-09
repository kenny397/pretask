package com.ssg.task.api.service;

import com.ssg.task.api.db.entity.Item;
import com.ssg.task.api.db.entity.Type;
import com.ssg.task.api.db.repository.ItemRepository;
import com.ssg.task.api.dto.request.ItemRequestDto;
import com.ssg.task.api.exception.ExistException;
import com.ssg.task.api.exception.NameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

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
        item.setDisplayStartDate(itemRequestDto.getDisplayStart());
        item.setDisplayEndDate(itemRequestDto.getDisplayEnd());
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
}
