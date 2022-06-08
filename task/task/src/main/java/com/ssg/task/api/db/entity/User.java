package com.ssg.task.api.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class User extends BaseEntity{
    private String name;
    @Enumerated(EnumType.STRING)
    private UserType type;
    @Enumerated(EnumType.STRING)
    private UserStatus status;

}
