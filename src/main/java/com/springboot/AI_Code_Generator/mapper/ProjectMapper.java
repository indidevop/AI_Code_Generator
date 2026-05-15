package com.springboot.AI_Code_Generator.mapper;

import com.springboot.AI_Code_Generator.dto.project.ProjectResponse;
import com.springboot.AI_Code_Generator.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponse projectToProjectResponse(Project project);
}
