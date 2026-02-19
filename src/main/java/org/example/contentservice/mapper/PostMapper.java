package org.example.contentservice.mapper;

import org.example.contentservice.dto.PostDTO;
import org.example.contentservice.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDTO toDTO(Post post);

    Post toEntity(PostDTO postDTO);
}
