package org.example.contentservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.example.contentservice.config.RabbitConfig;
import org.example.contentservice.dto.event.UserJoinedRoomEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoomEventListener {

    @RabbitListener(queues = RabbitConfig.QUEUE_JOIN)
    public void handleUserJoined(UserJoinedRoomEvent event) {
        log.info("Received UserJoinedRoomEvent from RabbitMQ: {}", event);
        
        // This is where you would implement specific logic for ContentService
        // example: notify other users in the room or update some local cache/stats.
        
        log.info("Successfully processed join event for user {} in room {}", 
                 event.getUserId(), event.getRoomId());
    }
}
