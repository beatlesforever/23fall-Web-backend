package com.example.forum.demos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.forum.demos.entity.User;
import com.example.forum.demos.mapper.UserMapper;
import com.example.forum.demos.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean register(User user) {
        // 实现注册逻辑
        if (userExists(user.getUsername())) {
            return false;
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userMapper.insert(user);
        return true;
    }

    private boolean userExists(String username) {
        // 实现检查用户名是否存在的逻辑
        return userMapper.findByUsername(username) != null;
    }

    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // 密码匹配，登录成功
            return user;
        }
        return null; // 登录失败
    }
}
