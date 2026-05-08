package com.springboot.AI_Code_Generator.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class User {
    private Long id;

    private String email;
    private String name;
    private String passwordHashed;
    private String avatarUrl;

    private Instant createdAt;
    private Instant updateAt;
    private Instant deletedAt;

}
