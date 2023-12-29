package com.example.forum.demos.controller;

import com.example.forum.demos.entity.User;
import com.example.forum.demos.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        boolean success = userService.register(user);
        if (success) {
            return ResponseEntity.ok("注册成功");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名已存在");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            // 实现创建会话或生成令牌的逻辑
            return ResponseEntity.ok("登陆成功");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("非法的用户名或密码");
    }

    /**
     * 获取所有用户的信息。
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.list());
    }

    /**
     * 根据用户 ID 获取单个用户的信息。
     */
    @GetMapping("/{userID}")
    public ResponseEntity<User> getUserById(@PathVariable Long userID) {
        return ResponseEntity.ok(userService.getById(userID));
    }

    /**
     * 更新用户信息。
     */
    @PutMapping("/{userID}")
    public ResponseEntity<User> updateUser(@PathVariable Long userID, @RequestBody User user) {
        user.setUserID(userID);
        userService.updateById(user);
        return ResponseEntity.ok(user);
    }

    /**
     * 删除用户。
     */
    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userID) {
        userService.removeById(userID);
        return ResponseEntity.ok().build();
    }
}
