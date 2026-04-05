package org.example.contentservice.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.contentservice.config.RabbitConfig;
import org.example.contentservice.dto.event.NewContentEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
