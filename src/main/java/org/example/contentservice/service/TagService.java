package org.example.contentservice.service;

import org.example.contentservice.model.Tag;

import java.util.Set;

public interface TagService {
    Set<Tag> findOrCreateTags(Set<String> tagNames);
    Set<String> getTagNames(Set<Tag> tags);
}
