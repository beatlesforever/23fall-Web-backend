package com.example.forum.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.forum.demos.entity.User;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
public interface IUserService extends IService<User> {
    boolean register(User user);
    User login(String username, String password);
}
