package com.example.springwebmvc.service.impl;

import com.example.springwebmvc.dto.CreateProductDto;
import com.example.springwebmvc.dto.UpdateProductDto;
import com.example.springwebmvc.model.Product;
import com.example.springwebmvc.model.Supplier;
import com.example.springwebmvc.repository.ProductRepository;
import com.example.springwebmvc.service.ProductService;
import com.example.springwebmvc.utils.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void create(CreateProductDto product) {

        // Map DTO to POJO
        Product newProduct = Product.builder()
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .inStock(product.inStock())
                .supplier(Supplier.builder().id(product.supplierId()).build())
                .build();

        if (product.name()!=null) {
            newProduct.setSlug(SlugUtil.toSlug(product.name()));
        }

        // Check if the product already exists
        if (repository.existsBySlug(newProduct.getSlug())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product with slug already exists: " + newProduct.getSlug());
        }

        // Start inserting a product
        productRepository.insertProduct(newProduct);
        // Start inserting product categories
        product.categoryIds().forEach(id -> productRepository.insertProductCategories(newProduct.getId(), id));
    }

    @Override
    @Transactional
    public void update(Integer id, UpdateProductDto product) {

        // Check if the product exists before updating
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found with ID: " + id);
        }

        //Map from DTO to POJO
        Product updateProduct = Product.builder()
                .name(product.name())
                .description(product.description())
                .supplier(Supplier.builder().id(product.supplierId()).build())
                .build();
        if (product.name()!=null) {
            updateProduct.setSlug(SlugUtil.toSlug(product.name()));
        }
        updateProduct.setId(id);
        // Start updating a product
        productRepository.updateProduct(updateProduct);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // Check if the product exists before deleting
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Product findById(Integer id) {
        Product product = repository.findById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found with ID: " + id);
        }
        return product;
    }

    @Override
    public Product findBySlug(String slug) {

        Product product = repository.findBySlug(slug);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found with Slug: " + slug);
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> searchByNameAndStatus(String name, Boolean status) {

        return repository.searchByNameAndStatus(name, status);
    }
}

