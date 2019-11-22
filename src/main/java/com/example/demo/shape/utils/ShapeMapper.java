package com.example.demo.shape.utils;


import com.example.demo.shape.dto.ShapeCategoryDto;
import com.example.demo.shape.dto.ShapeDto;
import com.example.demo.shape.model.Shape;
import com.example.demo.shape.model.ShapeCategory;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(ShapeMapperDecorator.class)
public interface ShapeMapper {

    ShapeMapper INSTANCE = Mappers.getMapper(ShapeMapper.class);

    @Mapping(target = "attributes", ignore = true)
    ShapeCategoryDto toDto(ShapeCategory shapeCategory);

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "areaFormula", expression= "java(shapeCategoryDto.getAreaFormula().toLowerCase())")
    @Mapping(target = "name", expression= "java(shapeCategoryDto.getName().toUpperCase())")
    ShapeCategory toEntity(ShapeCategoryDto shapeCategoryDto);

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "catId", source = "category.id")
    @Mapping(target = "name", source = "category.name")
    ShapeDto toDto(Shape shape);

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "attributesMap", ignore = true)
    Shape toEntity(ShapeDto shapeDto);





}

