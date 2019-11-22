package com.example.demo.shape.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShapeCategorySimpleDto {
    private Long id;
    private String name;
    private String areaFormula;
}
