package com.ssg.task.api.db.repository;

import com.ssg.task.api.dto.ItemDto;
import com.ssg.task.api.dto.ItemSearchCondition;



import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemDto> isPurchase(ItemSearchCondition itemSearchCondition);
}
