package com.springboot.AI_Code_Generator.service;

import com.springboot.AI_Code_Generator.dto.file.FileContentResponse;
import com.springboot.AI_Code_Generator.dto.project.FileNode;

import java.util.List;

public interface FileService {
    List<FileNode> getFileTree(Long projectId, Long userId);

    FileContentResponse getFile(Long projectId, Long userId, String path);

    void saveFile(Long projectId, String filePath, String fileContent);
}
