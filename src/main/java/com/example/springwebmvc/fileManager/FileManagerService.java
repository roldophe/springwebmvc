package com.example.springwebmvc.fileManager;

import com.example.springwebmvc.fileManager.web.FileManagerDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileManagerService {

    Resource downloadByName(String name);
    FileManagerDto uploadSingle(MultipartFile file);
    List<FileManagerDto> uploadMultiple(List<MultipartFile> files);
    FileManagerDto findByName(String name);
    List<FileManagerDto> findAll();
    void deleteAll();
    void deleteByName(String name);
}
