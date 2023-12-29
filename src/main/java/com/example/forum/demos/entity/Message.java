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
@TableName("Messages")
public class Message {
    @TableId(value="messageID",type = IdType.AUTO)
    private Long messageID;
    @TableField("senderID")
    private Long senderID;
    @TableField("recipientID")
    private Long recipientID;
    @TableField("content")
    private String content;
    @TableField("dateTime")
    private LocalDateTime dateTime;
}