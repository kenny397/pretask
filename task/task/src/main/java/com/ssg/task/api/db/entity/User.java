package com.ssg.task.api.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class User extends BaseEntity{
    private String name;
    private String type;
    private String stat;

}
