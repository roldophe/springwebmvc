package com.example.springwebmvc.repository;

import com.example.springwebmvc.model.Product;
import com.example.springwebmvc.repository.provider.ProductProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductRepository {

    // -------------------------------
    // Find all products (with supplier & categories)
    // -------------------------------
    @Select("SELECT * FROM products ORDER BY id DESC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "slug", column = "slug"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "inStock", column = "in_stock"),
            @Result(property = "supplier", column = "supplier_id",
                    one = @One(select = "com.example.springwebmvc.repository.SupplierRepository.findById")),
            @Result(property = "categories", column = "id",
                    many = @Many(select = "com.example.springwebmvc.repository.CategoryRepository.selectProductCategories"))
    })
    List<Product> findAll();

    // -------------------------------
    // Find product by ID
    // -------------------------------
    @Select("SELECT * FROM products WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "slug", column = "slug"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "inStock", column = "in_stock"),
            @Result(property = "supplier", column = "supplier_id",
                    one = @One(select = "com.example.springwebmvc.repository.SupplierRepository.findById")),
            @Result(property = "categories", column = "id",
                    many = @Many(select = "com.example.springwebmvc.repository.CategoryRepository.selectProductCategories"))
    })
    Product findById(Integer id);

    // -------------------------------
    // Find product by slug
    // -------------------------------
    @Select("SELECT * FROM products WHERE slug = #{slug}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "slug", column = "slug"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "inStock", column = "in_stock"),
            @Result(property = "supplier", column = "supplier_id",
                    one = @One(select = "com.example.springwebmvc.repository.SupplierRepository.findById")),
            @Result(property = "categories", column = "id",
                    many = @Many(select = "com.example.springwebmvc.repository.CategoryRepository.selectProductCategories"))
    })
    Product findBySlug(String slug);

    // -------------------------------
    // Insert a new product
    // -------------------------------
    @InsertProvider(ProductProvider.class)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertProduct(@Param("pro") Product product);

    // -------------------------------
    // Update product
    // -------------------------------
    @UpdateProvider(ProductProvider.class)
    void updateProduct(@Param("pro") Product product);

    // -------------------------------
    // Delete product by ID
    // -------------------------------
    @Delete("DELETE FROM products WHERE id = #{id}")
    void deleteById(Integer id);

    // -------------------------------
    // Product-Category Many-to-Many handlers
    // -------------------------------
    @InsertProvider(ProductProvider.class)
    void insertProductCategories(@Param("proId") Integer productId,
                                 @Param("catId") Integer categoryId);
    @InsertProvider(ProductProvider.class)
    void deleteProductCategories(@Param("proId") Integer productId);

    @Select("SELECT EXISTS (SELECT 1 FROM products WHERE slug = #{slug})")
    boolean existsBySlug(String slug);
    // -------------------------------
    // Global search by name and status
    // -------------------------------
    @Select("""
        SELECT * FROM products
        WHERE LOWER(name) LIKE CONCAT('%', LOWER(#{name}), '%')
          AND in_stock = #{status}
    """)
    List<Product> searchByNameAndStatus(@Param("name") String name,
                                        @Param("status") Boolean status);
}
