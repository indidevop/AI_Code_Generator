package com.springboot.AI_Code_Generator.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.util.Set;

import static com.springboot.AI_Code_Generator.enums.ProjectPermission.*;

@Getter
@RequiredArgsConstructor
public enum ProjectRole {
    EDITOR(Set.of(EDIT, VIEW, DELETE, VIEW_MEMBERS)),
    VIEWER(Set.of(VIEW, VIEW_MEMBERS)),
    OWNER(Set.of(DELETE, EDIT, VIEW, MANAGE_MEMBERS, VIEW_MEMBERS));

    private final Set<ProjectPermission> permissions;
}
