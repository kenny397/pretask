package com.ssg.task.api.service;


import com.ssg.task.api.dto.ItemDto;
import com.ssg.task.api.dto.request.ItemRequestDto;
import com.ssg.task.api.dto.request.UserRequestDto;

import java.util.List;

public interface ItemService {
    public String addItem(ItemRequestDto itemRequestDto);
    public void deleteItem(String itemName);
    public List<ItemDto> isPurchasItem(String userName);
}
