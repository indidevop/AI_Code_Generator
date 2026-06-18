package com.springboot.AI_Code_Generator.mapper;

import com.springboot.AI_Code_Generator.dto.chat.ChatResponse;
import com.springboot.AI_Code_Generator.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    List<ChatResponse> ListOfChatMessageToListOfChatResponse(List<ChatMessage> chatMessageList);
}
