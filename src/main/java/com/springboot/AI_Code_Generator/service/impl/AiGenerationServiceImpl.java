package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.llm.PromptUtils;
import com.springboot.AI_Code_Generator.llm.advisors.FileTreeContextAdvisor;
import com.springboot.AI_Code_Generator.llm.tools.CodeGenerationTools;
import com.springboot.AI_Code_Generator.security.AuthUtil;
import com.springboot.AI_Code_Generator.service.AiGenerationService;
import com.springboot.AI_Code_Generator.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
@Slf4j
public class AiGenerationServiceImpl implements AiGenerationService {

    private final ChatClient chatClient;
    private final AuthUtil authUtil;
    private final FileService fileService;
    private final FileTreeContextAdvisor fileTreeContextAdvisor;

    private static final Pattern FILE_TAG_PATTERN = Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);


    // Flux is an async stream data type
    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public Flux<String> streamResponse(String userPrompt, Long projectId) {

           log.info("Called stream response");

            Long userId = authUtil.getCurrentUserId();
            createChatSessionIfNotExists(projectId, userId);

            Map<String, Object> advisorParams = Map.of(
                    "userId", userId,
                    "projectId", projectId
            );

            StringBuilder fullResponseBuffer = new StringBuilder();


           CodeGenerationTools codeGenerationTools = new CodeGenerationTools(fileService,projectId);


            return chatClient.prompt()
                    .system(PromptUtils.CODE_GENERATION_SYSTEM_PROMPT)
                    .user(userPrompt)
                    .tools(codeGenerationTools)
                    .advisors(a -> {
                        a.params(advisorParams);
                        a.advisors(fileTreeContextAdvisor);
                    })
                    .stream()
                    .chatResponse()
                    .doOnNext(response -> {
                                // here we get chunk of data
                                String content = response.getResult().getOutput().getText();
                                                 fullResponseBuffer.append(content);
                    })
                    .doOnComplete(() -> {
                        Schedulers.boundedElastic().schedule(() -> {
                        parseAndSaveFiles(fullResponseBuffer.toString(), projectId);
//                            long duration = (endTime.get() - startTime.get()) /  1000;
//                            finalizeChats(userMessage, chatSession, fullResponseBuffer.toString(), duration, usageRef.get());
                        });

                    })
                    .doOnError(Throwable::printStackTrace)
                    .map(r -> r.getResult().getOutput().getText());

    }

    // This method will store data in minio, so it is expensive.
    // Instead of running it on the same thread we are running it on different thread
    private void parseAndSaveFiles(String response, Long projectId) {

        Matcher matcher = FILE_TAG_PATTERN.matcher(response);

        while(matcher.find())
        {
            String filePath = matcher.group(1);
            String fileContent = matcher.group(2).trim();

            fileService.saveFile(projectId, filePath, fileContent);
        }
    }

    private void createChatSessionIfNotExists(Long projectId, Long userId) {

    }
}
