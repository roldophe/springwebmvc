package com.example.springwebmvc.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateProductDto(

        @Size(max = 100, message = "Product name must not exceed 100 characters")
        String name,

        @Size(max = 255, message = "Description must not exceed 255 characters")
        String description,

        @Positive(message = "Supplier ID must be a positive number")
        Integer supplierId
) {}
