package org.example.contentservice.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.contentservice.config.RabbitConfig;
import org.example.contentservice.dto.event.AchievementEventType;
import org.example.contentservice.dto.event.NewContentEvent;
import org.example.contentservice.dto.event.UserActionEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendNewContentEvent(Long contentId, String title, Long userId, Long roomId, String type) {
        NewContentEvent event = NewContentEvent.builder()
                .id(contentId)
                .title(title)
                .userId(userId)
                .roomId(roomId)
                .contentType(type)
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Sending new content event to RabbitMQ: {}", event);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_CONTENT, RabbitConfig.ROUTING_KEY_CONTENT, event);
    }

    public void sendUserActionEvent(Long userId, AchievementEventType type, Long targetId, Long directionId) {
        UserActionEvent event = UserActionEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .userId(userId)
                .type(type)
                .targetId(targetId)
                .directionId(directionId)
                .build();

        log.info("Sending User Action Event to Statistics: {}", event);
        String routingKey = "user.action." + type.name().toLowerCase().replace("_", ".");
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_STATISTICS, routingKey, event);
    }
}
