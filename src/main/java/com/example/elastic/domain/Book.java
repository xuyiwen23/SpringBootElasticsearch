package com.example.elastic.domain;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author xyw
 * @date 2020/12/02
 */
public class Book {
    @Field(type = FieldType.Integer)
    public int size;

    @Field(type = FieldType.keyword)
    public String name;

    public Book() {

    }

    public Book(int size, String name) {
        this.size = size;
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "size=" + size +
                ", name='" + name + '\'' +
                '}';
    }
}
