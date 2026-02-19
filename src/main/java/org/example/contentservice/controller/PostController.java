package org.example.contentservice.controller;

import org.example.contentservice.dto.PostDTO;
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
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO dto) {
        Post post = postMapper.toEntity(dto);
        Post saved = postService.createPost(post);
        return ResponseEntity.ok(postMapper.toDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(postMapper.toDTO(post));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<PostDTO>> getPostsByRoom(@PathVariable Long roomId) {
        List<PostDTO> dtos = postService.getPostsByRoom(roomId)
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId) {
        List<PostDTO> dtos = postService.getPostsByUser(userId)
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id,
                                              @RequestBody PostDTO dto) {
        Post updatedEntity = postMapper.toEntity(dto);
        Post updated = postService.updatePost(id, updatedEntity);
        return ResponseEntity.ok(postMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
