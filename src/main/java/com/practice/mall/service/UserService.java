package com.practice.mall.service;

import com.practice.mall.exception.MallException;
import com.practice.mall.model.pojo.User;

public interface UserService {
    User getUser();

    void register(String username, String password) throws MallException;

    User login(String userName, String password) throws MallException;

    void updateInformation(User user) throws MallException;

    boolean checkAdminRole(User user);
}
