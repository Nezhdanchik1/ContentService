package org.example.contentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.contentservice.model.Comment;
import org.example.contentservice.model.Post;
import org.example.contentservice.repository.CommentRepository;
import org.example.contentservice.repository.PostRepository;
import org.example.contentservice.service.AchievementEventPublisher;
import org.example.contentservice.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AchievementEventPublisher eventPublisher;

    @Override
    public Comment addComment(Comment comment) {

        Long postId = comment.getPost().getId();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        comment.setPost(post);
        Comment saved = commentRepository.save(comment);

        // Отправка события в AchievementService
        eventPublisher.publishEvent(
                saved.getUserId(),
                "COMMENT_ADDED",
                saved.getId(),
                null // У постов пока нет directionId
        );

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getCommentsById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment acceptAnswer(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // снимаем предыдущий accepted если есть
        Comment previous = commentRepository
                .findByPostIdAndIsAcceptedTrue(comment.getPost().getId());

        if (previous != null) {
            previous.setAccepted(false);
        }

        comment.setAccepted(true);

        return comment;
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.delete(comment);
    }
}
