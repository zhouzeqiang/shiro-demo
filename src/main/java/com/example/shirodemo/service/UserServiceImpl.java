package com.example.shirodemo.service;

import com.example.shirodemo.dao.UserRepository;
import com.example.shirodemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouzeqiang
 * @date 2019/11/15
 * @description
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
