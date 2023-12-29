package com.example.forum.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.forum.demos.entity.Post;

import java.util.List;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
public interface IPostService extends IService<Post> {
    List<Post> getPostsByUserId(Long userId);
    List<Post> searchPostsByTitle(String title);
}
