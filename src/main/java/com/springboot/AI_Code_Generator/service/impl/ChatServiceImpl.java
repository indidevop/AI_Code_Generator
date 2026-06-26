package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.chat.ChatResponse;
import com.springboot.AI_Code_Generator.entity.ChatMessage;
import com.springboot.AI_Code_Generator.entity.ChatSession;
import com.springboot.AI_Code_Generator.entity.ChatSessionId;
import com.springboot.AI_Code_Generator.mapper.ChatMapper;
import com.springboot.AI_Code_Generator.repository.ChatMessageRepository;
import com.springboot.AI_Code_Generator.repository.ChatSessionRepository;
import com.springboot.AI_Code_Generator.security.AuthUtil;
import com.springboot.AI_Code_Generator.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final AuthUtil authUtil;
    private final ChatMapper chatMapper;

    @Override
    public List<ChatResponse> getChatHistory(Long projectId) {
        Long currentUserId = authUtil.getCurrentUserId();
        ChatSessionId chatSessionId = new ChatSessionId(projectId, currentUserId);

        ChatSession chatSession = chatSessionRepository.findById(chatSessionId).orElse(null);
        if (chatSession == null) {
            return List.of();
        }

        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatSession(chatSession);

        return chatMapper.ListOfChatMessageToListOfChatResponse(chatMessageList);
    }
}
