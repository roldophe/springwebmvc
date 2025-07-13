package com.example.springwebmvc.fileManager.web;

import com.example.springwebmvc.fileManager.FileManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileManagerController {

    private final FileManagerService fileService;

    @GetMapping(value = "download/{name}")
    public ResponseEntity<Resource> download(@PathVariable String name) {
        Resource resource = fileService.downloadByName(name);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + resource.getFilename())
                .body(resource);
    }

    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileManagerDto uploadSingle(@RequestPart MultipartFile file) {
        return fileService.uploadSingle(file);
    }

    @PostMapping(value = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<FileManagerDto> uploadMultiple(@RequestPart List<MultipartFile> files) {
        return fileService.uploadMultiple(files);
    }

    @GetMapping("/{name}")
    public FileManagerDto findByName(@PathVariable String name) {
        return fileService.findByName(name);
    }

    @GetMapping
    public List<FileManagerDto> findAll() {
        return fileService.findAll();
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByName(@PathVariable String name) {
        fileService.deleteByName(name);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        fileService.deleteAll();
    }

}
