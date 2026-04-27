package org.example.contentservice.controller;

import org.example.contentservice.dto.response.CommentResponse;
import org.example.contentservice.dto.request.CreateCommentRequest;
import org.example.contentservice.mapper.CommentMapper;
import org.example.contentservice.model.Comment;
import org.example.contentservice.repository.PostRepository;
import org.example.contentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final org.example.contentservice.repository.ArticleRepository articleRepository;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CreateCommentRequest commentRequest) {
        Comment comment = commentMapper.toEntity(commentRequest);
        
        // Пытаемся найти либо пост, либо статью
        var post = postRepository.findById(commentRequest.getPostId());
        if (post.isPresent()) {
            comment.setPost(post.get());
        } else {
            // Если поста нет, проверяем статью
            var article = articleRepository.findById(commentRequest.getPostId());
            if (article.isPresent()) {
                comment.setArticle(article.get());
            } else {
                throw new RuntimeException("Target content not found with ID: " + commentRequest.getPostId());
            }
        }

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
        // Здесь postId может быть как от Post, так и от Article
        List<CommentResponse> dtos = commentService.getCommentsByPost(postId)
                .stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
        
        // Если ничего не нашли через Post, пробуем через Article
        if (dtos.isEmpty()) {
            dtos = commentService.getCommentsByArticle(postId)
                    .stream()
                    .map(commentMapper::toResponse)
                    .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUser(@PathVariable Long userId) {
        List<CommentResponse> dtos = commentService.getCommentsByUser(userId)
                .stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/accept")
    @PreAuthorize("@securityService.isPostAuthorByCommentId(#id, principal)")
    public ResponseEntity<CommentResponse> acceptAnswer(@PathVariable Long id) {
        Comment accepted = commentService.acceptAnswer(id);
        System.out.println(accepted.isAccepted());
        return ResponseEntity.ok(commentMapper.toResponse(accepted));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or @securityService.isCommentAuthor(#id, principal)")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
