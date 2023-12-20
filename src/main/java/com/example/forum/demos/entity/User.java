package com.example.forum.demos.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@Data
@TableName("Users")
public class User {
    @TableId("UserID")
    private Long userID;
    private String username;
    private String password;
    private String email;
    // 其他属性
}
