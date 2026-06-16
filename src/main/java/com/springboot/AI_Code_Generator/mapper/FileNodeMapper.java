package com.springboot.AI_Code_Generator.mapper;

import com.springboot.AI_Code_Generator.dto.project.FileNode;
import com.springboot.AI_Code_Generator.entity.ProjectFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileNodeMapper {
    List<FileNode> toListOfFileNode(List<ProjectFile> filesList);
}
