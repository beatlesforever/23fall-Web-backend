package com.example.forum.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.demos.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM Users WHERE username = #{username}")
    User findByUsername(String username);
}
