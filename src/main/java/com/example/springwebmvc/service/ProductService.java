package com.example.springwebmvc.service;

import com.example.springwebmvc.dto.CreateProductDto;
import com.example.springwebmvc.dto.UpdateProductDto;
import com.example.springwebmvc.model.Product;

import java.util.List;

public interface ProductService {
    void create(CreateProductDto product);
    void update(Integer id, UpdateProductDto product);
    void delete(Integer id);
    Product findById(Integer id);
    Product findBySlug(String slug);
    List<Product> findAll();
    List<Product> searchByNameAndStatus(String name, Boolean status);
}