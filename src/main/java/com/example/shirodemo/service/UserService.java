package com.example.shirodemo.service;

import com.example.shirodemo.entity.User;

/**
 * @author zhouzeqiang
 * @date 2019/11/15
 * @description
 */
public interface UserService {

    User findByUserName(String userName);
}
