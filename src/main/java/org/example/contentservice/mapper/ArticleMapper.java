package org.example.contentservice.mapper;

import org.example.contentservice.dto.request.CreateArticleRequest;
import org.example.contentservice.dto.response.ArticleResponse;
import org.example.contentservice.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {WikiMapper.class})
public interface ArticleMapper {
    ArticleResponse toResponse(Article article);

    Article toEntity(CreateArticleRequest createArticleRequest);
}
