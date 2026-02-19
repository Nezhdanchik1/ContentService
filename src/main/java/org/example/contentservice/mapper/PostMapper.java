package org.example.contentservice.mapper;

import org.example.contentservice.dto.request.CreatePostRequest;
import org.example.contentservice.dto.response.OnePostResponse;
import org.example.contentservice.dto.response.PostResponse;
import org.example.contentservice.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostResponse toResponse(Post post);
    OnePostResponse toOnePostResponse(Post post);

    Post toEntity(CreatePostRequest postRequest);
}
