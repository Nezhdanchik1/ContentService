package org.example.contentservice.service;

import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.Article;

import java.util.List;
import java.util.Set;

public interface ArticleService {

    Article createArticle(Article article, Set<String> tagNames);

    Article getById(Long id);

    List<Article> getByRoom(Long roomId);

    Article updateArticle(Long id, Article updated, Set<String> tagNames);

    void deleteArticle(Long id);

    Article updateAIStatus(Long id, AIStatus status, Double score);
}
