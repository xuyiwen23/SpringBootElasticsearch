package com.example.elastic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.elastic.domain.User;
import com.example.elastic.repository.UserRepository;

/**
 * @author xyw
 * @date 2020/12/02
 */
@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User findOne() {
        User user = userRepository.findById("25").get();
        return user;
    }
}
