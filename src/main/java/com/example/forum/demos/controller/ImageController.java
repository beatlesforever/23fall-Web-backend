package com.example.forum.demos.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.forum.demos.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private static final String UPLOAD_DIR = "uploaded_images/";

    @Autowired
    private IService<Image> imageService;

    /**
     * 上传图片
     */
    @PostMapping
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file) {
        String filePath = handleFileUpload(file);
        if (filePath != null) {
            Image image = new Image();
            image.setImagePath(filePath);
            imageService.save(image);
            return ResponseEntity.status(HttpStatus.CREATED).body(image);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 获取特定图片的信息
     */
    @GetMapping("/{imageID}")
    public ResponseEntity<Image> getImageById(@PathVariable Long imageID) {
        Image image = imageService.getById(imageID);
        if (image != null) {
            return ResponseEntity.ok(image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 删除特定图片
     */
    @DeleteMapping("/{imageID}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageID) {
        boolean isRemoved = imageService.removeById(imageID);
        if (isRemoved) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 文件处理方法
    private String handleFileUpload(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            if(originalFileName.contains("..")) {
                // 安全检查
                return null;
            }

            // 生成新的文件名
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension; // 使用UUID生成唯一的文件名

            // 文件保存路径
            Path uploadPath = Paths.get(UPLOAD_DIR);

            // 如果目录不存在，则创建目录
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 保存文件
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);

            return filePath.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
