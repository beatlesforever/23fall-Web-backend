package com.example.forum.demos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableId(value = "userID",type = IdType.AUTO)
    private Long userID;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("email")
    private String email;
    // 其他属性
}
