package com.example.forum.demos.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.forum.demos.entity.Image;
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
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private IService<Image> imageService;

    /**
     * 获取所有图片信息。
     */
    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.ok(imageService.list());
    }

    /**
     * 根据图片 ID 获取单个图片的信息。
     */
    @GetMapping("/{imageId}")
    public ResponseEntity<Image> getImageById(@PathVariable Long imageId) {
        return ResponseEntity.ok(imageService.getById(imageId));
    }

    /**
     * 创建新图片。
     */
    @PostMapping
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        imageService.save(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }

    /**
     * 更新图片信息。
     */
    @PutMapping("/{imageId}")
    public ResponseEntity<Image> updateImage(@PathVariable Long imageId, @RequestBody Image image) {
        image.setImageID(imageId);
        imageService.updateById(image);
        return ResponseEntity.ok(image);
    }

    /**
     * 删除图片。
     */
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageService.removeById(imageId);
        return ResponseEntity.ok().build();
    }
}
