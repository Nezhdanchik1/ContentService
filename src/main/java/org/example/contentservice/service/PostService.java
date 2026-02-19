package org.example.contentservice.service;

import org.example.contentservice.model.Post;

import java.util.List;

public interface PostService {

    Post createPost(Post post);

    Post getPostById(Long id);

    List<Post> getPostsByRoom(Long roomId);

    List<Post> getPostsByUser(Long userId);

    Post updatePost(Long id, Post updatedPost);

    void deletePost(Long id);
}
