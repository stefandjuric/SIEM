package com.example.siem.controller;

import com.example.siem.domain.AgentData;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.RequestHandledEvent;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by djuro on 6/27/2018.
 */
@Controller
public class MyWebController {

    private AgentData agentData;

    private Boolean eventFlag = false;

    @RequestMapping("/sseTest")
    public SseEmitter handleRequest () {

        final SseEmitter emitter = new SseEmitter();
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            for (int i = 0; i < 500; i++) {
                try {
                    emitter.send(LocalTime.now().toString() , MediaType.TEXT_PLAIN);

                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.completeWithError(e);
                    return;
                }
            }
            emitter.complete();
        });

        return emitter;
    }


    @EventListener
    public void handleEvent (RequestHandledEvent e) {
        String[] parts = e.getDescription().split(";");
        String url=(parts[0].split("="))[1].replace("[","").replace("]","");
        //if(url.equals("/addAgentData"))

    }
}
