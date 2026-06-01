package org.example.contentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.Article;
import org.example.contentservice.model.DifficultyLevel;
import org.example.contentservice.repository.ArticleRepository;
import org.example.contentservice.service.AchievementEventPublisher;
import org.example.contentservice.service.ArticleService;
import org.example.contentservice.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagService tagService;
    private final AchievementEventPublisher eventPublisher;

    @Override
    public Article createArticle(Article article, Set<String> tagNames) {
        if (article.getDifficultyLevel() == null) {
            article.setDifficultyLevel(DifficultyLevel.BEGINNER);
        }
        article.setAiReviewStatus(AIStatus.PENDING);
        article.setTags(tagService.findOrCreateTags(tagNames != null ? tagNames : Set.of()));
        
        Article saved = articleRepository.save(article);
        
        // Отправка события в AchievementService
        eventPublisher.publishEvent(
                saved.getUserId(),
                "ARTICLE_PUBLISHED",
                saved.getId(),
                saved.getDirectionId()
        );
        
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Article getById(Long id) {
        return articleRepository.findDetailedById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getByRoom(Long roomId) {
        return articleRepository.findByRoomId(roomId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getByUser(Long userId) {
        return articleRepository.findByUserId(userId);
    }

    @Override
    public Article updateArticle(Long id, Article updated, Set<String> tagNames) {
        Article existing = getById(id);

        existing.setTitle(updated.getTitle());
        existing.setContent(updated.getContent());
        existing.setDifficultyLevel(updated.getDifficultyLevel());
        existing.setTags(tagService.findOrCreateTags(tagNames));

        return existing;
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.findById(id).ifPresent(articleRepository::delete);
    }

    @Override
    public Article updateAIStatus(Long id, AIStatus status, Double score) {
        Article article = getById(id);
        article.setAiReviewStatus(status);
        article.setAiScore(score);
        return article;
    }
}
