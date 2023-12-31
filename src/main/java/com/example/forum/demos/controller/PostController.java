package com.example.forum.demos.controller;

import com.example.forum.demos.entity.Post;
import com.example.forum.demos.service.IPostService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zhouhaoran
 * @date 2023/12/19
 * @project forum
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private IPostService postService;
    private static final String UPLOAD_DIR = "uploaded_images/";


    /**
     * 获取帖子列表
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.list();
        return ResponseEntity.ok(posts);
    }

    /**
     * 获取特定帖子
     */
    @GetMapping("/{postID}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postID) {
        Post post = postService.getById(postID);
        return ResponseEntity.ok(post);
    }


    @PostMapping("/uploadImage")
    public ResponseEntity<List<String>> uploadImage(@RequestParam("files") MultipartFile[] files) {
        List<String> filePaths = uploadImages(files);
        if (!filePaths.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(filePaths);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 创建帖子
     */
//    @PostMapping
//    public ResponseEntity<Post> createPost(@RequestParam("title") String title,
//                                           @RequestParam("content") String content,
//                                           @RequestParam("userID") Long userID,
//                                           @RequestParam("imagePaths") List<String> imagePaths) {
//        Post post = new Post();
//        post.setTitle(title);
//        post.setContent(content);
//        post.setUserID(userID);
//        post.setDateTime(LocalDateTime.now()); // 设置当前时间
//        post.setImagePathArray(imagePaths.toArray(new String[0]));
//
//        postService.save(post);
//        return ResponseEntity.status(HttpStatus.CREATED).body(post);
//    }
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        post.setDateTime(LocalDateTime.now()); // 设置当前时间
        postService.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/{postID}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postID, @RequestBody Post postUpdate) {
        Post existingPost = postService.getById(postID);
        if (existingPost == null) {
            return ResponseEntity.notFound().build();
        }

        existingPost.setTitle(postUpdate.getTitle());
        existingPost.setContent(postUpdate.getContent());
        if (postUpdate.getImagePathArray() != null && postUpdate.getImagePathArray().length > 0) {
            deleteOldImages(existingPost.getImagePathArray());
            existingPost.setImagePathArray(postUpdate.getImagePathArray());
        }

        postService.updateById(existingPost);
        return ResponseEntity.ok(existingPost);
    }

    /**
     * 删除帖子
     */
    @DeleteMapping("/{postID}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postID) {
        postService.removeById(postID);
        return ResponseEntity.ok().build();  //返回一个状态码为 200（OK） 的 HTTP 响应。
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable Long userID) {
        List<Post> userPosts = postService.getPostsByUserId(userID);
        if (!userPosts.isEmpty()) {
            return ResponseEntity.ok(userPosts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Post>> searchPostsByTitle(@PathVariable String query) {
        List<Post> searchedPosts = postService.searchPostsByTitle(query);
        if (!searchedPosts.isEmpty()) {
            return ResponseEntity.ok(searchedPosts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // 文件处理方法
    private List<String> uploadImages(MultipartFile[] files) {
        List<String> filePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
                    if(originalFileName.contains("..")) {
                        continue; // 安全检查
                    }

                    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String newFileName = UUID.randomUUID().toString() + fileExtension; // 使用UUID生成唯一的文件名

                    Path uploadPath = Paths.get(UPLOAD_DIR);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    Path filePath = uploadPath.resolve(newFileName);
                    Files.copy(file.getInputStream(), filePath);

                    filePaths.add(filePath.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return filePaths;
    }
    // 删除旧图片文件的方法
    private void deleteOldImages(String[] oldImagePaths) {
        if (oldImagePaths == null) return;

        for (String path : oldImagePaths) {
            try {
                Path fileToDeletePath = Paths.get(path);
                Files.deleteIfExists(fileToDeletePath);
            } catch (IOException e) {
                e.printStackTrace();
                // 处理删除文件的异常，如记录日志
            }
        }
    }
}

