package com.example.forum.demos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.forum.demos.entity.Post;
import com.example.forum.demos.mapper.PostMapper;
import com.example.forum.demos.service.IPostService;
import org.springframework.stereotype.Service;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
}
