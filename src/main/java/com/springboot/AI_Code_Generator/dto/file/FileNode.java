package com.springboot.AI_Code_Generator.dto.file;

import java.time.Instant;

public record FileNode(
        String path,
        Instant modifiedAt,
        String type,
        Long size
) {
}
