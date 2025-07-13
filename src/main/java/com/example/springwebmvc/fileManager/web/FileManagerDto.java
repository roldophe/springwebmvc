package com.example.springwebmvc.fileManager.web;


import lombok.Builder;

@Builder
public record FileManagerDto(String name,
                             String uri,
                             String downloadUri,
                             Long size,
                             String extension) {
}
