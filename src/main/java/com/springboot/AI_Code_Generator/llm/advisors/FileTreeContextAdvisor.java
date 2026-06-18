package com.springboot.AI_Code_Generator.llm.advisors;

import com.springboot.AI_Code_Generator.dto.project.FileNode;
import com.springboot.AI_Code_Generator.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileTreeContextAdvisor implements StreamAdvisor {

    private final FileService fileService;

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain streamAdvisorChain) {
        Map<String,Object> context = request.context();

        Long projectId = Long.parseLong(context.getOrDefault("projectId",0).toString());

        ChatClientRequest augmentedChatClientRequest = augmentedRequsetWithFileTree(request,projectId);

        return streamAdvisorChain.nextStream(augmentedChatClientRequest);
    }

    private ChatClientRequest augmentedRequsetWithFileTree(ChatClientRequest request, Long projectId) {

        // following is done for LLM caching
        List<Message> incomingMessages = request.prompt().getInstructions();

        Message systemMessage = incomingMessages.stream()
                .filter(m -> m.getMessageType() == MessageType.SYSTEM)
                .findFirst()
                .orElse(null);

        List<Message> userMessages = incomingMessages.stream()
                .filter(m -> m.getMessageType() != MessageType.SYSTEM)
                .toList();

        List<Message> allMessages = new ArrayList<>();

        // Add original system message
        if (systemMessage != null) {
            allMessages.add(systemMessage);
        }

        List<FileNode> fileTree = fileService.getFileTree(projectId);
        String fileTreeContext = "\n\n ---- FILE_TREE ----\n"+fileTree.toString();
        allMessages.add(new SystemMessage(fileTreeContext));

        allMessages.addAll(userMessages);

        return request
                .mutate()
                .prompt(new Prompt(allMessages, request.prompt().getOptions()))
                .build();

//        1. Previous system msg + 2. New system msg (file tree) +3. All user messages


    }

    @Override
    public String getName() {
        return "FileTreeContextAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

// LLMs also do caching
// Cache hit and cache miss affects cost
// If multiple prompts are used, some previous prompt data cached