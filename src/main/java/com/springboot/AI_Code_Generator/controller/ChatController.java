package com.springboot.AI_Code_Generator.controller;

import com.springboot.AI_Code_Generator.dto.chat.ChatRequest;
import com.springboot.AI_Code_Generator.dto.chat.ChatResponse;
import com.springboot.AI_Code_Generator.service.AiGenerationService;
import com.springboot.AI_Code_Generator.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final AiGenerationService aiGenerationService;
    private final ChatService chatService;

    @PostMapping(value = "/api/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamChat(
            @RequestBody ChatRequest request) {

        return aiGenerationService.streamResponse(request.message(), request.projectId())
                .map(data -> ServerSentEvent.<String>builder()
                        .data(data)
                        .build());

    }

    @PostMapping(value = "/projects/{projectId}")
    public ResponseEntity<List<ChatResponse>> getChatHistory(Long projectId){
        return ResponseEntity.ok(chatService.getChatHistory(projectId));
    }



}
