package org.example.contentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.contentservice.config.RabbitConfig;
import org.example.contentservice.dto.UserActionEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishEvent(Long userId, String type, Long targetId, Long directionId) {
        if (userId == null) return;

        UserActionEvent event = UserActionEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .userId(userId)
                .type(type)
                .targetId(targetId)
                .directionId(directionId)
                .build();

        String routingKey = "user.action." + type.toLowerCase().replace("_", ".");
        
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_STATISTICS, routingKey, event);
            log.info("Published achievement event: {} for user {}", type, userId);
        } catch (Exception e) {
            log.error("Failed to publish achievement event", e);
        }
    }
}
