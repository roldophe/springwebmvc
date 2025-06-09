package com.example.springwebmvc.dto;

import java.util.List;

public record UpdateProductDto(String name,
                               String description,
                               Integer supplierId,
                               List<Integer> categoryIds) {
}
