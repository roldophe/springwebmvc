package com.example.springwebmvc.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public record CreateProductDto(

        @NotBlank(message = "Product name is required")
        @Size(max = 100, message = "Product name must not exceed 100 characters")
        String name,

        @Size(max = 255, message = "Description must not exceed 255 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Stock status is required")
        Boolean inStock,

        @NotNull(message = "Supplier ID is required")
        @Positive(message = "Supplier ID must be a positive number")
        Integer supplierId,

        @NotEmpty(message = "At least one category ID is required")
        List<@NotNull(message = "Category ID cannot be null") @Positive(message = "Category ID must be positive") Integer> categoryIds

) {}