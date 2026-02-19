package org.example.contentservice.controller;

import org.example.contentservice.dto.ArticleDTO;
import org.example.contentservice.mapper.ArticleMapper;
import org.example.contentservice.model.Article;
import org.example.contentservice.model.AIStatus;
import org.example.contentservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO dto) {
        Article entity = articleMapper.toEntity(dto);
        Article saved = articleService.createArticle(entity);
        return ResponseEntity.ok(articleMapper.toDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable Long id) {
        Article article = articleService.getById(id);
        return ResponseEntity.ok(articleMapper.toDTO(article));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ArticleDTO>> getArticlesByRoom(@PathVariable Long roomId) {
        List<ArticleDTO> dtos = articleService.getByRoom(roomId)
                .stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id,
                                                    @RequestBody ArticleDTO dto) {
        Article updatedEntity = articleMapper.toEntity(dto);
        Article updated = articleService.updateArticle(id, updatedEntity);
        return ResponseEntity.ok(articleMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ai-review")
    public ResponseEntity<ArticleDTO> updateAIReview(@PathVariable Long id,
                                                     @RequestParam AIStatus status,
                                                     @RequestParam(required = false) Double score) {
        Article article = articleService.updateAIStatus(id, status, score);
        return ResponseEntity.ok(articleMapper.toDTO(article));
    }
}
