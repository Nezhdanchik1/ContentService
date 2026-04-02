package org.example.contentservice.controller;

import org.example.contentservice.dto.request.CreateArticleRequest;
import org.example.contentservice.dto.response.ArticleResponse;
import org.example.contentservice.mapper.ArticleMapper;
import org.example.contentservice.model.Article;
import org.example.contentservice.model.AIStatus;
import org.example.contentservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody CreateArticleRequest dto) {
        Article entity = articleMapper.toEntity(dto);
        Article saved = articleService.createArticle(entity, dto.getTags());
        return ResponseEntity.ok(articleMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) {
        Article article = articleService.getById(id);
        return ResponseEntity.ok(articleMapper.toResponse(article));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ArticleResponse>> getArticlesByRoom(@PathVariable Long roomId) {
        List<ArticleResponse> dtos = articleService.getByRoom(roomId)
                .stream()
                .map(articleMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or @securityService.isArticleAuthor(#id, principal)")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable Long id,
                                                         @RequestBody CreateArticleRequest dto) {
        Article updatedEntity = articleMapper.toEntity(dto);
        Article updated = articleService.updateArticle(id, updatedEntity, dto.getTags());
        return ResponseEntity.ok(articleMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or @securityService.isArticleAuthor(#id, principal)")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ai-review")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ArticleResponse> updateAIReview(@PathVariable Long id,
                                                          @RequestParam AIStatus status,
                                                          @RequestParam(required = false) Double score) {
        Article article = articleService.updateAIStatus(id, status, score);
        return ResponseEntity.ok(articleMapper.toResponse(article));
    }
}
