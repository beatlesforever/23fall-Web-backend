package com.example.forum.demos.controller;

import com.example.forum.demos.entity.Post;
import com.example.forum.demos.service.IPostService;
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
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private IPostService postService;

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

    /**
     * 创建帖子
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        postService.save(post);  //将客户端传来的 post 对象保存到数据库中
        //返回一个 HTTP 响应，状态码为 201（表示成功创建），并将保存后的文章作为响应的主体返回给客户端。
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    /**
     * 更新帖子
     */
    @PutMapping("/{postID}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postID, @RequestBody Post post) {
        //@RequestBody Post post 客户端发送的 JSON 数据会被映射到 Post 对象中
        post.setPostID(postID);  //将路径变量中的 postID 设置到 post 对象中，以便在更新数据库时使用。
        postService.updateById(post);  //将更新后的 post 对象保存到数据库中。
        return ResponseEntity.ok(post);
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

}

