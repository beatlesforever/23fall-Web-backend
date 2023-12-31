package com.example.forum.demos.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.forum.demos.entity.Message;
import com.example.forum.demos.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private IService<Message> messageService;

    /**
     * 获取所有留言信息。
     */
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(messageService.list());
    }

    /**
     * 根据留言 ID 获取单个留言的信息。
     */
    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long messageId) {
        return ResponseEntity.ok(messageService.getById(messageId));
    }

    /**
     * 创建新留言。
     */
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        message.setSenderID(currentUser.getUserID()); // 假设有一个senderId字段
        messageService.save(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    /**
     * 删除留言。
     */
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // 从数据库中获取留言信息
        Message message = messageService.getById(messageId);
        if (message == null) {
            // 如果留言不存在，返回404
            return ResponseEntity.notFound().build();
        }
        // 检查当前用户是否是留言的发送者
        if (!message.getSenderID().equals(currentUser.getUserID())) {
            // 如果不是发送者，拒绝删除操作
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        messageService.removeById(messageId);
        return ResponseEntity.ok().build();
    }

}
