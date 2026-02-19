package org.example.contentservice.controller;

import org.example.contentservice.dto.CommentDTO;
import org.example.contentservice.mapper.CommentMapper;
import org.example.contentservice.model.Comment;
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

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO dto) {
        Comment comment = commentMapper.toEntity(dto);
        Comment saved = commentService.addComment(comment);
        return ResponseEntity.ok(commentMapper.toDTO(saved));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentDTO> dtos = commentService.getCommentsByPost(postId)
                .stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<CommentDTO> acceptAnswer(@PathVariable Long id) {
        Comment accepted = commentService.acceptAnswer(id);
        return ResponseEntity.ok(commentMapper.toDTO(accepted));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
