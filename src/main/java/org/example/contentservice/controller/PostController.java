package org.example.contentservice.controller;

import org.example.contentservice.dto.request.CreatePostRequest;
import org.example.contentservice.dto.response.OnePostResponse;
import org.example.contentservice.dto.response.PostResponse;
import org.example.contentservice.mapper.PostMapper;
import org.example.contentservice.model.Post;
import org.example.contentservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest postRequest) {
        Post post = postMapper.toEntity(postRequest);
        Post saved = postService.createPost(post);
        return ResponseEntity.ok(postMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OnePostResponse> getPost(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(postMapper.toOnePostResponse(post));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<PostResponse>> getPostsByRoom(@PathVariable Long roomId) {
        List<PostResponse> dtos = postService.getPostsByRoom(roomId)
                .stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable Long userId) {
        List<PostResponse> dtos = postService.getPostsByUser(userId)
                .stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id,
                                                   @RequestBody CreatePostRequest postRequest) {
        Post updatedEntity = postMapper.toEntity(postRequest);
        Post updated = postService.updatePost(id, updatedEntity);
        return ResponseEntity.ok(postMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
