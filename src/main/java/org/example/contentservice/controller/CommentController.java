package org.example.contentservice.controller;

import org.example.contentservice.dto.response.CommentResponse;
import org.example.contentservice.dto.request.CreateCommentRequest;
import org.example.contentservice.mapper.CommentMapper;
import org.example.contentservice.model.Comment;
import org.example.contentservice.repository.PostRepository;
import org.example.contentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestBody CreateCommentRequest commentRequest) {
        Comment comment = commentMapper.toEntity(commentRequest);
        comment.setPost(postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found")));

        Comment saved = commentService.addComment(comment);
        return ResponseEntity.ok(commentMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentsById(id);
        return ResponseEntity.ok(commentMapper.toResponse(comment));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponse> dtos = commentService.getCommentsByPost(postId)
                .stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<CommentResponse> acceptAnswer(@PathVariable Long id) {
        Comment accepted = commentService.acceptAnswer(id);
        System.out.println(accepted.isAccepted());
        return ResponseEntity.ok(commentMapper.toResponse(accepted));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
