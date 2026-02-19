package org.example.contentservice.mapper;

import org.example.contentservice.dto.response.CommentResponse;
import org.example.contentservice.dto.request.CreateCommentRequest;
import org.example.contentservice.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "accepted", target = "isAccepted")
    CommentResponse toResponse(Comment comment);

    @Mapping(source = "postId", target = "post.id")
    Comment toEntity(CreateCommentRequest commentRequest);

}