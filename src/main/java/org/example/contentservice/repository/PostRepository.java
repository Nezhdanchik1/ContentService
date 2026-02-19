package org.example.contentservice.repository;

import org.example.contentservice.model.Post;
import org.example.contentservice.model.PostType;
import org.example.contentservice.model.AIStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Все посты комнаты
    List<Post> findByRoomId(Long roomId);

    // Посты пользователя
    List<Post> findByUserId(Long userId);

    // Фильтр по типу
    List<Post> findByRoomIdAndPostType(Long roomId, PostType postType);

    // Только одобренные
    List<Post> findByRoomIdAndAiStatus(Long roomId, AIStatus aiStatus);
}
