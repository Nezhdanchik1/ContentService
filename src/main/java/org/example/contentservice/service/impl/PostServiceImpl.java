package org.example.contentservice.service.impl;

import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.Post;
import org.example.contentservice.repository.PostRepository;
import org.example.contentservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        post.setAiStatus(AIStatus.PENDING);
        return postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPostsByRoom(Long roomId) {
        return postRepository.findByRoomId(roomId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPostsByUser(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public Post updatePost(Long id, Post updatedPost) {
        Post existing = getPostById(id);

        existing.setTitle(updatedPost.getTitle());
        existing.setContent(updatedPost.getContent());
        existing.setPostType(updatedPost.getPostType());

        return postRepository.save(existing);
    }

    @Override
    public void deletePost(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }
}
