package com.example.springwebmvc.repository;

import com.example.springwebmvc.model.Category;
import com.example.springwebmvc.repository.provider.CategoryProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CategoryRepository {

    @SelectProvider(CategoryProvider.class)
    List<Category> selectProductCategories(@Param("product") Integer productId);

    @Select("SELECT * FROM categories")
    List<Category> findAllCategories();

    @Select("""
        SELECT *
        FROM categories
        WHERE name = #{name}
    """)
    Optional<Category> findCategoryByName(@Param("name") String name);

    @Select("""
        SELECT EXISTS (
            SELECT 1
            FROM categories
            WHERE name = #{name}
        )
    """)
    Boolean existsCategoryByName(@Param("name") String name);

    @Select("""
        SELECT *
        FROM categories
        WHERE id = #{id}
    """)
    Optional<Category> findCategoryById(@Param("id") Integer id);

    @Insert("""
        INSERT INTO categories (name, description)
        VALUES (#{c.name}, #{c.description})
    """)
    void insertCategory(@Param("c") Category category);

    @Update("""
        UPDATE categories
        SET name = #{c.name},
            description = #{c.description}
        WHERE id = #{c.id}
    """)
    void updateCategory(@Param("c") Category category);

    @Delete("""
        DELETE FROM categories
        WHERE id = #{id}
    """)
    void deleteCategoryById(@Param("id") Integer id);
}
