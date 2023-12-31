package com.example.forum.demos.controller;

import com.example.forum.demos.entity.User;
import com.example.forum.demos.entity.UserDTO;
import com.example.forum.demos.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> loginUser(@RequestBody User loginUser, HttpServletRequest request) {
        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            request.getSession().setAttribute("user", user); // 在会话中存储用户信息
            UserDTO dto = convertToDto(user);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("非法的用户名或密码");
    }
    /**
     * 获取所有用户的信息。
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.list();
        List<UserDTO> dtos = users.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    /**
     * 根据用户 ID 获取单个用户的信息。
     */
    @GetMapping("/{userID}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userID) {
        User user = userService.getById(userID);
        UserDTO userDTO = convertToDto(user);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * 更新用户信息。
     */
    @PutMapping("/{userID}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userID, @RequestBody User user) {
        user.setUserID(userID);
        userService.updateById(user);
        User updatedUser = userService.getById(userID);
        UserDTO dto = convertToDto(updatedUser);
        return ResponseEntity.ok(dto);
    }


    /**
     * 删除用户。
     */
    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userID) {
        userService.removeById(userID);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        request.getSession().invalidate(); // 销毁会话
        return ResponseEntity.ok("注销成功");
    }

    // 将 User 对象转换为 UserDTO 对象
    private UserDTO convertToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserID(user.getUserID());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        // 设置其他字段
        return dto;
    }
}
