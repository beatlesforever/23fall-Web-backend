package com.example.forum.demos.entity;

import lombok.Data;

/**
 * @author zhouhaoran
 * @date 2023/12/29
 * @project forum
 */
@Data
public class UserDTO {
    private Long userID;
    private String username;
    private String email;
}
