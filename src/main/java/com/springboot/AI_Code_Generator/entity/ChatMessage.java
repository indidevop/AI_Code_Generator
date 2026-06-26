package com.springboot.AI_Code_Generator.entity;

import com.springboot.AI_Code_Generator.enums.ChatEventType;
import com.springboot.AI_Code_Generator.enums.MessageRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@ToString
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns(
            {
                    @JoinColumn(name = "projectId", referencedColumnName = "project_id", nullable = false),
                    @JoinColumn(name = "userId", referencedColumnName = "user_id", nullable = false)
            }
    )
    @ToString.Exclude
    ChatSession chatSession;

    @Column(columnDefinition = "text", nullable = false)
    String content;  // Null unless USER role

    @OneToMany(mappedBy = "chatMessage",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("sequenceOrder ASC")
    @ToString.Exclude
    List<ChatEvent> events;  // Null unless ASSISTANT role

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    MessageRole role;

    String toolCalls; // JSON Array of Tools Called

    Integer tokensUsed;

    @CreationTimestamp
    Instant createdAt;

}

