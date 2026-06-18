package com.springboot.AI_Code_Generator.entity;

import com.springboot.AI_Code_Generator.enums.ChatEventType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    ChatMessage chatMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ChatEventType type;

    @Column(nullable = false)
    Integer sequenceOrder;

    @Column(columnDefinition = "text")
    String content;

    String filePath;

    @Column(columnDefinition = "text")
    String metadata;
}
