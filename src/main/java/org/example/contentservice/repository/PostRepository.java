package org.example.contentservice.repository;

import org.example.contentservice.model.Post;
import org.example.contentservice.model.PostType;
import org.example.contentservice.model.AIStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"tags"})
    List<Post> findByRoomId(Long roomId);

    @EntityGraph(attributePaths = {"tags"})
    List<Post> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"tags", "comments"})
    @Query("SELECT p FROM Post p WHERE p.id = :id")
    Optional<Post> findDetailedById(@Param("id") Long id);

    @Query("""
        SELECT p.roomId, COUNT(p)
        FROM Post p
        WHERE p.roomId IN :roomIds
        GROUP BY p.roomId
    """)
    List<Object[]> countPostsByRoomIds(List<Long> roomIds);

    // Фильтр по типу
    List<Post> findByRoomIdAndPostType(Long roomId, PostType postType);

    // Только одобренные
    List<Post> findByRoomIdAndAiStatus(Long roomId, AIStatus aiStatus);
}
