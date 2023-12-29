package com.example.forum.demos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@Data
@TableName("Posts")
public class Post {
    @TableId(value="postID",type = IdType.AUTO)
    private Long postID;
    @TableField("title")
    private String title;
    @TableField("content")
    private String content;
    @TableField("dateTime")
    private LocalDateTime dateTime;
    @TableField("userID")
    private Long userID;
    @TableField("imageID")
    private Long imageID;
    // 其他属性
}
