package com.example.forum.demos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.forum.demos.entity.User;
import com.example.forum.demos.mapper.UserMapper;
import com.example.forum.demos.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean register(User user) {
        // 检查用户是否存在
        if (userExists(user.getUsername())) {
            return false;
        }
        // 对密码进行加密处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        return true;
    }

    private boolean userExists(String username) {
        // 实现检查用户名是否存在的逻辑
        return userMapper.findByUsername(username) != null;
    }

    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        // 检查用户是否存在并验证密码
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // 密码匹配，登录成功
        }
        return null; // 登录失败
    }
}
