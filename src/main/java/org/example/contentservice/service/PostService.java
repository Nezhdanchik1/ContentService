package org.example.contentservice.service;

import org.example.contentservice.model.Post;

import java.util.List;
import java.util.Map;

public interface PostService {

    Post createPost(Post post);

    Post getPostById(Long id);

    List<Post> getPostsByRoom(Long roomId);

    List<Post> getPostsByUser(Long userId);

    Map<Long, Long> getPostsCountByRoomIds(List<Long> roomIds);

    Post updatePost(Long id, Post updatedPost);

    void deletePost(Long id);
}
