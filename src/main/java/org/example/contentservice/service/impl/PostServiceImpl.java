package org.example.contentservice.service.impl;

import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.Post;
import org.example.contentservice.repository.PostRepository;
import org.example.contentservice.service.PostService;
import org.example.contentservice.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final org.example.contentservice.producer.ContentEventProducer contentEventProducer;

    @Override
    public Post createPost(Post post, Set<String> tagNames) {
        post.setAiStatus(AIStatus.PENDING);
        post.setTags(tagService.findOrCreateTags(tagNames));
        Post saved = postRepository.save(post);
        
        contentEventProducer.sendNewContentEvent(
                saved.getId(),
                saved.getTitle(),
                saved.getUserId(),
                saved.getRoomId(),
                "POST"
        );
        
        return saved;
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

    public Map<Long, Long> getPostsCountByRoomIds(List<Long> roomIds) {

        List<Object[]> results = postRepository.countPostsByRoomIds(roomIds);

        Map<Long, Long> map = new HashMap<>();

        for (Object[] row : results) {
            Long roomId = (Long) row[0];
            Long count = (Long) row[1];
            map.put(roomId, count);
        }

        for (Long roomId : roomIds) {
            map.putIfAbsent(roomId, 0L);
        }

        return map;
    }

    @Override
    public Post updatePost(Long id, Post updatedPost, Set<String> tagNames) {
        Post existing = getPostById(id);

        existing.setTitle(updatedPost.getTitle());
        existing.setContent(updatedPost.getContent());
        existing.setPostType(updatedPost.getPostType());
        existing.setTags(tagService.findOrCreateTags(tagNames));

        return postRepository.save(existing);
    }

    @Override
    public void deletePost(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }
}
