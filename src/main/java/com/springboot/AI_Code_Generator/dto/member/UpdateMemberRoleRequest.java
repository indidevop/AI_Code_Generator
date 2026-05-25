package com.springboot.AI_Code_Generator.dto.member;

import com.springboot.AI_Code_Generator.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull ProjectRole role) {
}
