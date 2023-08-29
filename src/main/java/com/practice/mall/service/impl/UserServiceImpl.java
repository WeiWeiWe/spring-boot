package com.practice.mall.service.impl;

import com.practice.mall.model.dao.UserMapper;
import com.practice.mall.model.pojo.User;
import com.practice.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }
}
