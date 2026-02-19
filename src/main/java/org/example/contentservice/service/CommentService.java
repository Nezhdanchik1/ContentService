package org.example.contentservice.service;

import org.example.contentservice.model.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(Comment comment);

    List<Comment> getCommentsByPost(Long postId);

    Comment acceptAnswer(Long commentId);

    void deleteComment(Long id);
}
