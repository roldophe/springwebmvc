package com.example.springwebmvc.repository.provider;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class CategoryProvider implements ProviderMethodResolver {

    private final String TB_CATEGORY = "categories";

    public String selectProductCategories() {
        return new SQL() {{
            SELECT("*");
            FROM(TB_CATEGORY + " c");
            INNER_JOIN("product_categories pc ON pc.category_id = c.id");
            WHERE("pc.product_id =#{productId}");
        }}.toString();
    }
    public String selectCategories(){
        return new
                SQL(){{
                    SELECT("*");
                    FROM(TB_CATEGORY);
                }}.toString();
    }
}
