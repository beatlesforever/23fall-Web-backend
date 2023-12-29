package com.example.forum.demos.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.forum.demos.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private IService<Image> imageService;

    /**
     * 上传图片
     */
    @PostMapping
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file) {
        Image image = new Image();
        String filePath = handleFileUpload(file);
        image.setImagePath(filePath);

        imageService.save(image); // 保存Image对象
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }

    /**
     * 获取特定图片的信息
     */
    @GetMapping("/{imageId}")
    public ResponseEntity<Image> getImageById(@PathVariable Long imageId) {
        Image image = imageService.getById(imageId);
        if (image != null) {
            return ResponseEntity.ok(image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 删除特定图片
     */
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        boolean isRemoved = imageService.removeById(imageId);
        if (isRemoved) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 文件处理方法
    private String handleFileUpload(MultipartFile file) {
        // 实现文件上传逻辑
        return "path/to/file";
    }
}
