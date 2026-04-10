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

    Article findFirstByOrderByAiScoreDesc();
    
    Article findFirstByRoomIdInOrderByAiScoreDesc(List<Long> roomIds);

    List<Article> findByIdIn(List<Long> ids);

    @org.springframework.data.jpa.repository.Query("SELECT new org.example.contentservice.dto.response.CategoryStatDto(t.name, COUNT(a.id)) " +
            "FROM Article a JOIN a.tags t GROUP BY t.name ORDER BY COUNT(a.id) DESC")
    List<org.example.contentservice.dto.response.CategoryStatDto> countArticlesByTag(org.springframework.data.domain.Pageable pageable);

    @org.springframework.data.jpa.repository.Query("SELECT new org.example.contentservice.dto.response.CategoryStatDto(t.name, COUNT(a.id)) " +
            "FROM Article a JOIN a.tags t WHERE a.roomId IN :roomIds GROUP BY t.name ORDER BY COUNT(a.id) DESC")
    List<org.example.contentservice.dto.response.CategoryStatDto> countArticlesByTagAndRoomIds(@org.springframework.data.repository.query.Param("roomIds") List<Long> roomIds, org.springframework.data.domain.Pageable pageable);

    @org.springframework.data.jpa.repository.Query("SELECT a FROM Article a WHERE " +
            "LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.content) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Article> searchByTitleOrContent(@org.springframework.data.repository.query.Param("query") String query);

    @org.springframework.data.jpa.repository.Query("SELECT a FROM Article a WHERE " +
            "a.roomId IN :roomIds AND (" +
            "LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.content) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Article> searchByTitleOrContentAndRoomIds(@org.springframework.data.repository.query.Param("query") String query, @org.springframework.data.repository.query.Param("roomIds") List<Long> roomIds);
}
