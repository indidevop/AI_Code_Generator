package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.file.FileContentResponse;
import com.springboot.AI_Code_Generator.dto.file.FileNode;
import com.springboot.AI_Code_Generator.service.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public List<FileNode> getFileTree(Long projectId, Long userId) {
        return List.of();
    }

    @Override
    public FileContentResponse getFile(Long projectId, Long userId, String path) {
        return null;
    }
}
