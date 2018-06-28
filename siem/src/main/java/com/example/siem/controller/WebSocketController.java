package com.example.siem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by djuro on 6/27/2018.
 */
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketController(SimpMessagingTemplate template)
    {
        this.template = template;
    }

    @MessageMapping("/send/message")
    public void onReceivedMesage(String message)
    {
        System.out.println("Radiiiiiiiiiiiiiiiiiii");
        this.template.convertAndSend("/chat", new SimpleDateFormat("HH:mm:ss").format(new Date())+"-"+message);
    }
}
