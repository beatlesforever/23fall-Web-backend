package com.example.forum.demos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.forum.demos.entity.Message;
import com.example.forum.demos.mapper.MessageMapper;
import com.example.forum.demos.service.IImageService;
import com.example.forum.demos.service.IMessageService;
import org.springframework.stereotype.Service;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
}
