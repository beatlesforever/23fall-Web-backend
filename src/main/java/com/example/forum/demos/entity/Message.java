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
@TableName("Messages")
public class Message {
    @TableId("MessageID")
    private Long messageID;
    private Long senderID;
    private Long recipientID;
    private String content;
    private LocalDateTime dateTime;
    // 其他属性
}