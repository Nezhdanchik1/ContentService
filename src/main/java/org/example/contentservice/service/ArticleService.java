package org.example.contentservice.service;

import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.Article;

import java.util.List;

public interface ArticleService {

    Article createArticle(Article article);

    Article getById(Long id);

    List<Article> getByRoom(Long roomId);

    Article updateArticle(Long id, Article updated);

    void deleteArticle(Long id);

    Article updateAIStatus(Long id, AIStatus status, Double score);
}
