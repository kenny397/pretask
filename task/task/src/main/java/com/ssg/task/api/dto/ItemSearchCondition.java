package com.ssg.task.api.dto;

import com.ssg.task.api.db.entity.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchCondition {
    String userName;
    Type userType;
}
