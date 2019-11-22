package com.example.demo.shape.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
public class ShapeDto {

    Long id;

    @NotNull
    Long catId;

    String name;

    Map<String, Double> attributes = new HashMap<>();

}
