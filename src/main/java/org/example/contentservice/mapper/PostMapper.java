package org.example.contentservice.mapper;

import org.example.contentservice.dto.request.CreatePostRequest;
import org.example.contentservice.dto.response.OnePostResponse;
import org.example.contentservice.dto.response.PostResponse;
import org.example.contentservice.model.Post;
import org.example.contentservice.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
    PostResponse toResponse(Post post);

    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
    OnePostResponse toOnePostResponse(Post post);

    @Mapping(target = "tags", ignore = true)
    Post toEntity(CreatePostRequest postRequest);

    @Named("mapTags")
    default Set<String> mapTags(Set<Tag> tags) {
        if (tags == null) {
            return Collections.emptySet();
        }
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
    }
}
