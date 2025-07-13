package com.example.springwebmvc.fileManager;

import com.example.springwebmvc.fileManager.web.FileManagerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileManagerServiceImpl implements FileManagerService {

    @Value("${file.server-path}")
    private String serverPath;

    @Value("${file.base-uri}")
    private String fileBaseUri;

    @Value("${file.download-uri}")
    private String fileDownloadUri;

    @Override
    public Resource downloadByName(String name) {
        Path path = Paths.get(serverPath, name);
        if (!Files.exists(path)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found!");
        }

        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid file path", e);
        }
    }

    @Override
    public FileManagerDto uploadSingle(MultipartFile file) {
        return saveFile(file);
    }

    @Override
    public List<FileManagerDto> uploadMultiple(List<MultipartFile> files) {
        List<FileManagerDto> dtos = new ArrayList<>();
        for (MultipartFile file : files) {
            dtos.add(saveFile(file));
        }
        return dtos;
    }

    @Override
    public FileManagerDto findByName(String name) {
        Path path = Paths.get(serverPath, name);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found or not a file.");
        }

        try {
            Resource resource = new UrlResource(path.toUri());
            if (!resource.isReadable()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File is not readable.");
            }

            return buildDto(name, resource);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading file", e);
        }
    }

    @Override
    public List<FileManagerDto> findAll() {
        Path dir = Paths.get(serverPath);
        List<FileManagerDto> result = new ArrayList<>();

        try (Stream<Path> files = Files.list(dir)) {
            files.filter(Files::isRegularFile).forEach(path -> {
                try {
                    Resource resource = new UrlResource(path.toUri());
                    if (resource.exists() && resource.isReadable()) {
                        result.add(buildDto(path.getFileName().toString(), resource));
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error reading file: " + path, e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Unable to list files in " + serverPath, e);
        }

        return result;
    }

    @Override
    public void deleteAll() {
        Path dir = Paths.get(serverPath);
        try (Stream<Path> files = Files.list(dir)) {
            files.forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to delete file: " + path, e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete all files", e);
        }
    }

    @Override
    public void deleteByName(String name) {
        Path path = Paths.get(serverPath, name);
        try {
            if (!Files.deleteIfExists(path)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + name, e);
        }
    }

    private FileManagerDto saveFile(MultipartFile file) {
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename(), "Original filename is required");
        String extension = getExtension(originalFilename);
        String name = UUID.randomUUID() + "." + extension;

        Path path = Paths.get(serverPath, name);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + name, e);
        }

        return FileManagerDto.builder()
                .name(name)
                .uri(fileBaseUri + name)
                .downloadUri(fileDownloadUri + name)
                .extension(extension)
                .size(file.getSize())
                .build();
    }

    private FileManagerDto buildDto(String fileName, Resource resource) throws IOException {
        return FileManagerDto.builder()
                .name(fileName)
                .uri(fileBaseUri + fileName)
                .downloadUri(fileDownloadUri + fileName)
                .extension(getExtension(fileName))
                .size(resource.contentLength())
                .build();
    }

    private String getExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return (dot >= 0 && dot < fileName.length() - 1) ? fileName.substring(dot + 1) : "unknown";
    }
}
