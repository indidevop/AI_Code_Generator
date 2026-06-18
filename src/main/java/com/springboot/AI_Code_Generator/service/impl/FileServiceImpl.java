package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.file.FileContentResponse;
import com.springboot.AI_Code_Generator.dto.project.FileNode;
import com.springboot.AI_Code_Generator.entity.Project;
import com.springboot.AI_Code_Generator.entity.ProjectFile;
import com.springboot.AI_Code_Generator.error.ResourceNotFoundException;
import com.springboot.AI_Code_Generator.mapper.FileNodeMapper;
import com.springboot.AI_Code_Generator.repository.ProjectFileRepository;
import com.springboot.AI_Code_Generator.repository.ProjectRepository;
import com.springboot.AI_Code_Generator.service.FileService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final ProjectRepository projectRepository;
    private final ProjectFileRepository projectFileRepository;
    private final FileNodeMapper fileNodeMapper;
    private final MinioClient minioClient;

    @Value("${minio.project-bucket}")
    private String projectBucket;

    private final String BUCKET_NAME = "projects";

    @Override
    public List<FileNode> getFileTree(Long projectId) {

        List<ProjectFile> filesList = projectFileRepository.findByProjectId(projectId);
        return fileNodeMapper.toListOfFileNode(filesList);
    }

    @Override
    public FileContentResponse getFileContent(Long projectId, String path) {
        String objectName = projectId + "/" + path;
        try (
                InputStream is = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(BUCKET_NAME)
                                .object(objectName)
                                .build())) {

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return new FileContentResponse(path, content);
        } catch (Exception e) {
            log.error("Failed to read file: {}/{}", projectId, path, e);
            throw new RuntimeException("Failed to read file content", e);
        }

    }

    @Override
    public void saveFile(Long projectId, String filePath, String content) {
        log.info("Saving file {}",filePath);
        // Save file metadata in postgres
        // Save file in minio
        Project project = projectRepository.findById(projectId).orElseThrow(()->{
            return new ResourceNotFoundException("Project ", projectId.toString());
        });

        String cleanPath = filePath.startsWith("/") ? filePath.substring(1) : filePath;

        String objectKey = projectId + "/" + cleanPath;

        // following code is from docs
        try {
            byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
            InputStream inputStream = new ByteArrayInputStream(contentBytes);
            // saving the file content
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(projectBucket)
                            .object(objectKey)
                            .stream(inputStream, contentBytes.length, -1)
                            .contentType(determineContentType(cleanPath))
                            .build());

            // Saving the metaData
            ProjectFile file = projectFileRepository.findByProjectIdAndPath(projectId, cleanPath)
                    .orElseGet(() -> ProjectFile.builder()
                            .project(project)
                            .path(cleanPath)
                            .minioObjectKey(objectKey)
                            .createdAt(Instant.now())
                            .build());

            file.setUpdatedAt(Instant.now());
            projectFileRepository.save(file);
            log.info("Saved file: {}", objectKey);

        } catch (Exception e) {
            log.error("Failed to save file {}/{}", projectId, cleanPath, e);
            throw new RuntimeException("File save failed", e);
        }


    }

    private String determineContentType(String path) {
        String type = URLConnection.guessContentTypeFromName(path);
        if (type != null) return type;
        if (path.endsWith(".jsx") || path.endsWith(".ts") || path.endsWith(".tsx")) return "text/javascript";
        if (path.endsWith(".json")) return "application/json";
        if (path.endsWith(".css")) return "text/css";

        return "text/plain";
    }
}
