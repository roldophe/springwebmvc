package com.example.springwebmvc.model;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
//Three annotations above are pojo
//
@Builder
@AllArgsConstructor
public class Supplier {
    private Integer id;
    private String company;
    private LocalDate since;
    private Boolean status;
}

