package com.springboot.AI_Code_Generator.mapper;

import com.springboot.AI_Code_Generator.dto.project.ProjectSummaryResponse;
import com.springboot.AI_Code_Generator.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectSummaryResponseMapper {
    ProjectSummaryResponse projectToProjectSummaryResponse(Project project);
}
