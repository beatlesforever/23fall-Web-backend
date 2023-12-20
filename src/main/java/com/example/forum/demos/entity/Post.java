package com.example.forum.demos.entity;

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
    @TableId("PostID")
    private Long postID;

    private String title;
    private String content;
    private LocalDateTime dateTime;
    // 其他属性
}
