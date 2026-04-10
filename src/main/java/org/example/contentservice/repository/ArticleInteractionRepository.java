package org.example.contentservice.repository;

import org.example.contentservice.model.ArticleInteraction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleInteractionRepository extends JpaRepository<ArticleInteraction, Long> {

    @Query("SELECT ai.articleId " +
           "FROM ArticleInteraction ai " +
           "WHERE ai.createdAt >= :startDate " +
           "GROUP BY ai.articleId " +
           "ORDER BY COUNT(ai) DESC")
    List<Long> findPopularArticleIdsSince(@Param("startDate") LocalDateTime startDate, Pageable pageable);

    @Query("SELECT ai.articleId " +
           "FROM ArticleInteraction ai JOIN Article a ON a.id = ai.articleId " +
           "WHERE ai.createdAt >= :startDate AND a.roomId IN :roomIds " +
           "GROUP BY ai.articleId " +
           "ORDER BY COUNT(ai) DESC")
    List<Long> findPopularArticleIdsByRoomIdsSince(@Param("startDate") LocalDateTime startDate, @Param("roomIds") List<Long> roomIds, Pageable pageable);

    Long countByArticleIdAndInteractionType(Long articleId, org.example.contentservice.model.InteractionType interactionType);
}
