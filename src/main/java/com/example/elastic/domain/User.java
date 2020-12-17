package com.example.elastic.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author xyw
 * @date 2020/12/01
 */
@Document(indexName = "xyw",type = "doc")
public class User {
    @Id
    public String id;

    @Field(type = FieldType.Integer)
    public int test_int;

    @Field(type = FieldType.keyword)
    public String testString;

    @Field(type = FieldType.Integer)
    public int test_2int;

    @Field(type = FieldType.keyword)
    public String test2String;

    @Field(type = FieldType.Integer)
    public int account_number;

    @Field(type = FieldType.Integer)
    public int balance;

    @Field(type = FieldType.keyword)
    public String firstname;

    @Field(type = FieldType.keyword)
    public String lastname;

    @Field(type = FieldType.Integer)
    public int age;

    @Field(type = FieldType.keyword)
    public String gender;

    @Field(type = FieldType.text)
    public String address;

    @Field(type = FieldType.keyword)
    public String employer;

    @Field(type = FieldType.keyword)
    public String email;

    @Field(type = FieldType.keyword)   
    public String city;

    @Field(type = FieldType.keyword)   
    public String state;

    @Field(type = FieldType.Nested)
    public List<Book> books;

    @Field(type = FieldType.Object)
    public Body body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAccount_number() {
        return account_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public int getTest_int() {
        return test_int;
    }

    public void setTest_int(int test_int) {
        this.test_int = test_int;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public int getTest_2int() {
        return test_2int;
    }

    public void setTest_2int(int test_2int) {
        this.test_2int = test_2int;
    }

    public String getTest2String() {
        return test2String;
    }

    public void setTest2String(String test2String) {
        this.test2String = test2String;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", test_int=" + test_int +
                ", testString='" + testString + '\'' +
                ", account_number=" + account_number +
                ", balance=" + balance +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", employer='" + employer + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", books=" + books +
                ", body=" + body +
                '}';
    }
}
