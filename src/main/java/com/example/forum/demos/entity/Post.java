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
    @TableField("imagePaths")
    private String imagePaths; // 存储多个图片路径，用逗号分隔

    // 不映射到数据库的字段，仅用于应用逻辑
    @TableField(exist = false)
    private String[] imagePathArray;

    public String[] getImagePathArray() {
        // 当imagePaths为空或仅包含空白时，返回空数组
        return (imagePaths == null || imagePaths.trim().isEmpty()) ? new String[0] : imagePaths.split(",");
    }

    public void setImagePathArray(String[] imagePathArray) {
        // 当imagePathArray为空或第一个元素为空字符串时，设置imagePaths为null或空字符串
        if (imagePathArray == null || imagePathArray.length == 0 || imagePathArray[0].isEmpty()) {
            this.imagePaths = "";
        } else {
            this.imagePaths = String.join(",", imagePathArray);
        }
    }

}
