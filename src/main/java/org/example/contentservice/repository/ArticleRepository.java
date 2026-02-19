package org.example.contentservice.repository;

import org.example.contentservice.model.DifficultyLevel;
import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByRoomId(Long roomId);

    List<Article> findByUserId(Long userId);

    List<Article> findByRoomIdAndDifficultyLevel(Long roomId, DifficultyLevel level);

    List<Article> findByAiReviewStatus(AIStatus status);
}
