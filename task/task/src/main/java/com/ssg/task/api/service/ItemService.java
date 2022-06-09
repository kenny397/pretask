package com.ssg.task.api.service;


import com.ssg.task.api.dto.request.ItemRequestDto;
import com.ssg.task.api.dto.request.UserRequestDto;

public interface ItemService {
    public String addItem(ItemRequestDto itemRequestDto);
    public void deleteItem(String itemName);


}
