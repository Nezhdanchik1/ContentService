package org.example.contentservice.security;

import lombok.RequiredArgsConstructor;
import org.example.contentservice.model.Article;
import org.example.contentservice.model.Comment;
import org.example.contentservice.model.Post;
import org.example.contentservice.repository.ArticleRepository;
import org.example.contentservice.repository.CommentRepository;
import org.example.contentservice.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("securityService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityService {

    private final ArticleRepository articleRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public boolean isArticleAuthor(Long articleId, String userId) {
        if (userId == null) return false;
        return articleRepository.findById(articleId)
                .map(article -> article.getUserId().toString().equals(userId))
                .orElse(false);
    }

    public boolean isPostAuthor(Long postId, String userId) {
        if (userId == null) return false;
        return postRepository.findById(postId)
                .map(post -> post.getUserId().toString().equals(userId))
                .orElse(false);
    }

    public boolean isCommentAuthor(Long commentId, String userId) {
        if (userId == null) return false;
        return commentRepository.findById(commentId)
                .map(comment -> comment.getUserId().toString().equals(userId))
                .orElse(false);
    }

    public boolean isPostAuthorByCommentId(Long commentId, String userId) {
        if (userId == null) return false;
        return commentRepository.findById(commentId)
                .map(comment -> comment.getPost().getUserId().toString().equals(userId))
                .orElse(false);
    }
}
