package dev.check.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate mess) {
        this.messagingTemplate = mess;
    }
    public void notifyFrontend(String status) {
        messagingTemplate.convertAndSend("/topic/newsletterSent", "sent");
    }

}
