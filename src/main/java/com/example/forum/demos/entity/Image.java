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
@TableName("Images")
public class Image {
    @TableId("ImageID")
    private Long imageID;
    private String imagePath;
    // 其他属性
}