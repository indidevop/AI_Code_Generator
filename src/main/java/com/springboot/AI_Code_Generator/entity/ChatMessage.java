package com.springboot.AI_Code_Generator.entity;

import com.springboot.AI_Code_Generator.enums.MessageRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns(
            {
                    @JoinColumn(name = "projectId", referencedColumnName = "projectId", nullable = false),
                    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
            }
    )
    ChatSession chatSession;

    @Column(columnDefinition = "text", nullable = false)
    String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    MessageRole role;

    String toolCalls; // JSON Array of Tools Called

    Integer tokensUsed;

    @CreationTimestamp
    Instant createdAt;
}

