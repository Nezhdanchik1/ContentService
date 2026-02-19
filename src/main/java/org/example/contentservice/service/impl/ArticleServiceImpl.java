package org.example.contentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.Article;
import org.example.contentservice.repository.ArticleRepository;
import org.example.contentservice.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Article createArticle(Article article) {
        article.setAiReviewStatus(AIStatus.PENDING);
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
    public Article updateArticle(Long id, Article updated) {
        Article existing = getById(id);

        existing.setTitle(updated.getTitle());
        existing.setContent(updated.getContent());
        existing.setDifficultyLevel(updated.getDifficultyLevel());

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
