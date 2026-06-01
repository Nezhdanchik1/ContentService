package org.example.contentservice.mapper;

import org.example.contentservice.dto.response.CommentResponse;
import org.example.contentservice.dto.request.CreateCommentRequest;
import org.example.contentservice.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "postId", expression = "java(comment.getPost() != null ? comment.getPost().getId() : (comment.getArticle() != null ? comment.getArticle().getId() : null))")
    @Mapping(target = "isAccepted", expression = "java(comment.isAccepted())")
    CommentResponse toResponse(Comment comment);

    @Mapping(target = "post", ignore = true)
    @Mapping(target = "article", ignore = true)
    Comment toEntity(CreateCommentRequest commentRequest);

}