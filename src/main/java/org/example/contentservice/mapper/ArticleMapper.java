package org.example.contentservice.mapper;

import org.example.contentservice.dto.ArticleDTO;
import org.example.contentservice.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {WikiMapper.class})
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDTO toDTO(Article article);

    Article toEntity(ArticleDTO articleDTO);
}
