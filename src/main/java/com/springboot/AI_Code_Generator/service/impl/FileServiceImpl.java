package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.file.FileContentResponse;
import com.springboot.AI_Code_Generator.dto.file.FileNode;
import com.springboot.AI_Code_Generator.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

    @Override
    public void saveFile(Long projectId, String filePath, String fileContent) {
        log.info("Saving file {}",filePath);
        // Save file metadata in postgres
        // Save file in minio
    }
}
