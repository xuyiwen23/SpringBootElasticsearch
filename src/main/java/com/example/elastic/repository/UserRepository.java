package com.example.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.elastic.domain.User;

/**
 * @author xyw
 * @date 2020/12/01
 */
public interface UserRepository extends ElasticsearchRepository<User, String> {

    List<User> findByAge(int age);

    User findFirstById();

    List<User> findByLastname(String lastname);

    long countByLastname(String lastname);

    long deleteByLastname(String lastname);

    List<User> removeByLastname(String lastname);
}