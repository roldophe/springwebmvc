package com.example.springwebmvc.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
//Follow rule encapsulation (Getter Setter and NoArgConstructor =POJO)
@AllArgsConstructor
@Builder
public class Product {
    private Integer id;
    private String slug;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean inStock;
    private Supplier supplier;
    private List<Category> categories;
}
