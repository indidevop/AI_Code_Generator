package com.springboot.AI_Code_Generator.controller;

import com.springboot.AI_Code_Generator.dto.chat.ChatRequest;
import com.springboot.AI_Code_Generator.dto.chat.ChatResponse;
import com.springboot.AI_Code_Generator.dto.chat.StreamResponse;
import com.springboot.AI_Code_Generator.service.AiGenerationService;
import com.springboot.AI_Code_Generator.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/chat")
public class ChatController {
    private final AiGenerationService aiGenerationService;
    private final ChatService chatService;

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<StreamResponse>> streamChat(
            @RequestBody ChatRequest request) {

        return aiGenerationService.streamResponse(request.message(), request.projectId())
                .map(data -> ServerSentEvent.<StreamResponse>builder()
                        .data(data)
                        .build());

    }

    @GetMapping(value = "/projects/{projectId}/history")
    public ResponseEntity<List<ChatResponse>> getChatHistory(@PathVariable Long projectId){
        return ResponseEntity.ok(chatService.getChatHistory(projectId));
    }



}
