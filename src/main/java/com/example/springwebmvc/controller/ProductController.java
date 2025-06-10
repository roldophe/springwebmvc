package com.example.springwebmvc.controller;

import com.example.springwebmvc.dto.CreateProductDto;
import com.example.springwebmvc.dto.UpdateProductDto;
import com.example.springwebmvc.model.Product;
import com.example.springwebmvc.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/by-slug")
    public ResponseEntity<Product> getProductBySlug(@RequestParam("slug") String slug) {
        Product product = productService.findBySlug(slug);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateProductDto request) {
        productService.create(request);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id,
                                       @Valid @RequestBody UpdateProductDto request) {
        productService.update(id, request);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false) Boolean status) {
        List<Product> result = productService.searchByNameAndStatus(name, status);
        return ResponseEntity.ok(result);
    }
}
