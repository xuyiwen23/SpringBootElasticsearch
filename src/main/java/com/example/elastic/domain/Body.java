package com.example.elastic.domain;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author xyw
 * @date 2020/12/02
 */
public class Body {
    @Field(type = FieldType.Integer)
    public int height;

    @Field(type = FieldType.Boolean)
    public Boolean health;

    @Field(type = FieldType.keyword)
    public String name;

    public Body() {
    }

    public Body(int height, Boolean health, String name) {
        this.height = height;
        this.health = health;
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Boolean getHealth() {
        return health;
    }

    public void setHealth(Boolean health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
