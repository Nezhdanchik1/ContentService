package org.example.contentservice.repository;

import org.example.contentservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Все комментарии поста
    List<Comment> findByPostId(Long postId);

    // Все комментарии пользователя
    List<Comment> findByUserId(Long userId);

    // Принятый ответ (для вопроса)
    Comment findByPostIdAndIsAcceptedTrue(Long postId);
}
