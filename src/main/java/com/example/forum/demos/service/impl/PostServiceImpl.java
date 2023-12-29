package com.example.forum.demos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.forum.demos.entity.Post;
import com.example.forum.demos.mapper.PostMapper;
import com.example.forum.demos.service.IPostService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
    @Override
    public List<Post> getPostsByUserId(Long userId) {
        // 使用MyBatis Plus的查询构造器来查询
        return lambdaQuery().eq(Post::getUserID, userId).list();
        //lambdaQuery() 方法创建了一个查询构造器,eq() 用于添加等值条件
    }

    @Override
    public List<Post> searchPostsByTitle(String title) {
        // 使用MyBatis Plus的查询构造器来实现模糊查询,而 like() 用于添加模糊匹配条件。
        return lambdaQuery().like(Post::getTitle, title).list();
    }
}

