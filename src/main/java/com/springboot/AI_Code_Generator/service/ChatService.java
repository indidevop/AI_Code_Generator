package com.springboot.AI_Code_Generator.service;


import com.springboot.AI_Code_Generator.dto.chat.ChatResponse;

import java.util.List;

public interface ChatService {
    List<ChatResponse> getChatHistory(Long projectId);
}
