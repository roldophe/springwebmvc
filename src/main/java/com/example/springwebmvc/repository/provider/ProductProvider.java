package com.example.springwebmvc.repository.provider;

import com.example.springwebmvc.model.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class ProductProvider implements ProviderMethodResolver {

    private final String TB_NAME = "products";

    public String insertProduct(){
        return new SQL() {{
            INSERT_INTO(TB_NAME);
            VALUES("name", "#{pro.name}");
            VALUES("slug", "#{pro.slug}");
            VALUES("description", "#{pro.description}");
            VALUES("price", "#{pro.price}");
            VALUES("in_stock", "#{pro.inStock}");
            VALUES("supplier_id", "#{pro.supplier.id}");
        }}.toString();
    }

    public String updateProduct(){
        return new SQL() {{
            UPDATE(TB_NAME);
            SET("name = #{pro.name}");
            SET("slug = #{pro.slug}");
            SET("description = #{pro.description}");
            SET("supplier_id = #{pro.supplier.id}");
            WHERE("id = #{pro.id}");
        }}.toString();
    }

    public String insertProductCategories() {
        return new SQL() {{
            INSERT_INTO("product_categories");
            VALUES("product_id", "#{proId}");
            VALUES("category_id", "#{catId}");
        }}.toString();
    }

    public String deleteProductCategories() {
        return new SQL() {{
            DELETE_FROM("product_categories");
            WHERE("product_id = #{proId}");
        }}.toString();
    }
}
