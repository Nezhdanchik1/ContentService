package org.example.contentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.contentservice.model.Comment;
import org.example.contentservice.model.Post;
import org.example.contentservice.repository.CommentRepository;
import org.example.contentservice.repository.PostRepository;
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

    @Override
    public Comment addComment(Comment comment) {

        Long postId = comment.getPost().getId();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        comment.setPost(post);
        return commentRepository.save(comment);
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
