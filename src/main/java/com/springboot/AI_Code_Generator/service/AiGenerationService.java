package com.springboot.AI_Code_Generator.service;


import com.springboot.AI_Code_Generator.dto.chat.StreamResponse;
import reactor.core.publisher.Flux;

public interface AiGenerationService {
    Flux<StreamResponse> streamResponse(String message, Long projectId);
}
