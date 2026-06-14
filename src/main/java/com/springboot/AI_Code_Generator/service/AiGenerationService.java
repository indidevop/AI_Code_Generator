package com.springboot.AI_Code_Generator.service;


import reactor.core.publisher.Flux;

public interface AiGenerationService {
    Flux<String> streamResponse(String message, Long projectId);
}
