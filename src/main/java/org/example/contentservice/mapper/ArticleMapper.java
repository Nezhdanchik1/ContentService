package org.example.contentservice.mapper;

import org.example.contentservice.dto.request.CreateArticleRequest;
import org.example.contentservice.dto.response.ArticleResponse;
import org.example.contentservice.model.Article;
import org.example.contentservice.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {WikiMapper.class})
public interface ArticleMapper {

    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
    ArticleResponse toResponse(Article article);

    @Mapping(target = "tags", ignore = true)
    Article toEntity(CreateArticleRequest createArticleRequest);

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
