package org.example.contentservice.repository;

import org.example.contentservice.model.DifficultyLevel;
import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @EntityGraph(attributePaths = {"tags", "wikiEntries"})
    List<Article> findByRoomId(Long roomId);

    @EntityGraph(attributePaths = {"tags", "wikiEntries"})
    List<Article> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"tags", "wikiEntries"})
    @Query("SELECT a FROM Article a WHERE a.id = :id")
    Optional<Article> findDetailedById(@Param("id") Long id);

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

    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT a FROM Article a LEFT JOIN a.tags t WHERE " +
            "LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.content) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Article> searchByTitleOrContent(@org.springframework.data.repository.query.Param("query") String query);

    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT a FROM Article a LEFT JOIN a.tags t WHERE " +
            "a.roomId IN :roomIds AND (" +
            "LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.content) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Article> searchByTitleOrContentAndRoomIds(@org.springframework.data.repository.query.Param("query") String query, @org.springframework.data.repository.query.Param("roomIds") List<Long> roomIds);
}
