package org.example.contentservice.repository;

import org.example.contentservice.model.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"post", "article"})
    List<Comment> findByPostId(Long postId);

    @EntityGraph(attributePaths = {"post", "article"})
    List<Comment> findByArticleId(Long articleId);

    // Все комментарии пользователя
    List<Comment> findByUserId(Long userId);

    // Принятый ответ (для вопроса)
    Comment findByPostIdAndIsAcceptedTrue(Long postId);

    Comment findByArticleIdAndIsAcceptedTrue(Long articleId);
}
