package com.example.springwebmvc.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//Three annotations above are pojo
//
@Builder
@AllArgsConstructor
public class Category {
    private Integer id;
    private String name;
    private String description;
}
