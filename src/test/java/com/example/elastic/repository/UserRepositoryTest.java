package com.example.elastic.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.elastic.domain.Body;
import com.example.elastic.domain.Book;
import com.example.elastic.domain.User;

/**
 * @author xyw
 * @date 2020/12/02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void test(){
        User user = userRepository.findById("10000").get();
        System.out.println(user);
    }

    @Test
    public void findByLastname(){
        List<User> users = userRepository.findByLastname("Ayala");
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void findByAge(){
        List<User> users = userRepository.findByAge(39);
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void updateUser(){
        User user = userRepository.findById("25").get();
        user.setLastname("Wayaha");
        user.setAge(45);
        User save = userRepository.save(user);
        System.out.println(user);
    }

    @Test
    public void insertUser(){
        User user = new User();
        user.setId("10005");
        user.setAge(10);
        user.setLastname("X");
        user.setTest_int(1000);

        User save = userRepository.save(user);
        System.out.println(user);
    }

    @Test
    public void insertUserWithBook(){
        User user = new User();
        user.setId("10000");
        user.setAge(10);
        user.setLastname("X");

        Book a = new Book(10, "A");
        Book b = new Book(11, "B");
        Book c = new Book(12, "C");

        ArrayList<Book> books = new ArrayList<>();
        books.add(a);
        books.add(b);
        books.add(c);

        user.setBooks(books);

        User save = userRepository.save(user);
        System.out.println(user);
    }

    @Test
    public void test01(){
        User user = userRepository.findById("100007").get();
        System.out.println(user);
    }

    @Test
    public void insertUserWithBodyBook(){
        User user = new User();
        user.setId("10005");
        user.setAge(10);
        user.setLastname("X");

        Book a = new Book(10, "A");
        Book b = new Book(11, "B");
        Book c = new Book(12, "C");

        ArrayList<Book> books = new ArrayList<>();
        books.add(a);
        books.add(b);
        books.add(c);

        user.setBooks(books);

        Body xyw = new Body(10, true, "xyw");
        user.setBody(xyw);

        User save = userRepository.save(user);
        System.out.println(user);
    }

}