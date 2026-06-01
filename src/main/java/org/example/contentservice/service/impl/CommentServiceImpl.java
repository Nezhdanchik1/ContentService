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
        if (comment.getPost() == null && comment.getArticle() == null) {
            throw new IllegalArgumentException("Comment must be linked to a post or an article");
        }
        if (comment.getPost() != null && comment.getArticle() != null) {
            throw new IllegalArgumentException("Comment cannot be linked to both post and article");
        }

        Comment saved = commentRepository.save(comment);

        // Отправка события в AchievementService
        eventPublisher.publishEvent(
                saved.getUserId(),
                "COMMENT_ADDED",
                saved.getId(),
                null
        );

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByArticle(Long articleId) {
        return commentRepository.findByArticleId(articleId);
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
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    @Override
    public Comment acceptAnswer(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Comment previous = null;
        if (comment.getPost() != null) {
            previous = commentRepository.findByPostIdAndIsAcceptedTrue(comment.getPost().getId());
        } else if (comment.getArticle() != null) {
            previous = commentRepository.findByArticleIdAndIsAcceptedTrue(comment.getArticle().getId());
        } else {
            throw new IllegalStateException("Comment is not linked to post or article");
        }

        if (previous != null && !previous.getId().equals(comment.getId())) {
            previous.setAccepted(false);
            commentRepository.save(previous);
        }

        comment.setAccepted(true);

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.delete(comment);
    }
}
