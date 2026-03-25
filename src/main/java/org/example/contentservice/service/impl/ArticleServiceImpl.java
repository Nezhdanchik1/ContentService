package org.example.contentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.Article;
import org.example.contentservice.repository.ArticleRepository;
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

    @Override
    public Article createArticle(Article article, Set<String> tagNames) {
        article.setAiReviewStatus(AIStatus.PENDING);
        article.setTags(tagService.findOrCreateTags(tagNames));
        return articleRepository.save(article);
    }

    @Override
    @Transactional(readOnly = true)
    public Article getById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getByRoom(Long roomId) {
        return articleRepository.findByRoomId(roomId);
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
        articleRepository.delete(getById(id));
    }

    @Override
    public Article updateAIStatus(Long id, AIStatus status, Double score) {
        Article article = getById(id);
        article.setAiReviewStatus(status);
        article.setAiScore(score);
        return article;
    }
}
