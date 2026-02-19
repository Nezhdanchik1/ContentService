package org.example.contentservice.mapper;

import org.example.contentservice.dto.CommentDTO;
import org.example.contentservice.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDTO toDTO(Comment comment);

    Comment toEntity(CommentDTO commentDTO);
}
