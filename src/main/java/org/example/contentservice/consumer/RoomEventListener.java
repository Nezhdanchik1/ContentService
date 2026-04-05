package org.example.contentservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.example.contentservice.config.RabbitConfig;
import org.example.contentservice.dto.event.UserJoinedRoomEvent;
import org.example.contentservice.dto.event.UserLeftRoomEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoomEventListener {

    @RabbitListener(queues = RabbitConfig.QUEUE_JOIN)
    public void handleUserJoined(UserJoinedRoomEvent event) {
        log.info("Received UserJoinedRoomEvent from RabbitMQ: {}", event);
        
        // Example: logic to greet or update local data
        
        log.info("Successfully processed join event for user {} in room {}", 
                 event.getUserId(), event.getRoomId());
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_LEAVE)
    public void handleUserLeft(UserLeftRoomEvent event) {
        log.info("Received UserLeftRoomEvent from RabbitMQ: {}", event);
        
        // Example: logic to cleanup or update stats
        
        log.info("Successfully processed leave event for user {} from room {}", 
                 event.getUserId(), event.getRoomId());
    }
}
