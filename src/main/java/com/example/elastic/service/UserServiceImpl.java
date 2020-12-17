package com.example.elastic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public User findOne() {
        User user = userRepository.findById("25").get();
        return user;
    }

    @Override
    public void test(){
        for (int i = 0; i < 10000; i++) {
            logger.debug("debug");
            logger.warn("warm");
            logger.error("error");
        }
    }
}
