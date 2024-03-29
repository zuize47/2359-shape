package com.example.demo.shape.utils;

import com.example.demo.shape.dto.ShapeCategoryDto;
import com.example.demo.shape.dto.ShapeDto;
import com.example.demo.shape.model.Shape;
import com.example.demo.shape.model.ShapeAttribute;
import com.example.demo.shape.model.ShapeAttributeValue;
import com.example.demo.shape.model.ShapeCategory;

import java.util.stream.Collectors;

public class ShapeMapperDecorator implements ShapeMapper {

    private final ShapeMapper delegate;

    public ShapeMapperDecorator(ShapeMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public ShapeCategoryDto toDto(ShapeCategory source) {
        ShapeCategoryDto dto = delegate.toDto( source );
        dto.setAttributes(source.getAttributes().stream().map(ShapeAttribute::getAttributeName).collect(Collectors.toSet()));
        return dto;
    }

    @Override
    public ShapeCategory toEntity(ShapeCategoryDto source) {
        ShapeCategory entity = delegate.toEntity(source);
        source.getAttributes().forEach(f -> {
            ShapeAttribute attribute = new ShapeAttribute();
            attribute.setAttributeName(f.toLowerCase());
            attribute.setCategory(entity);
            entity.getAttributes().add(attribute);
        });
        return entity;
    }

    @Override
    public ShapeDto toDto(Shape shape) {
        ShapeDto dto = delegate.toDto(shape);
        shape.getAttributes().forEach(e -> {
            dto.getAttributes().put(e.getAttributeName(), e.getValue());
        });
        return dto;
    }

    @Override
    public Shape toEntity(ShapeDto shapeDto) {
        final Shape entity = delegate.toEntity(shapeDto);
        shapeDto.getAttributes().entrySet().forEach(e -> {
            ShapeAttributeValue attributeValue = new ShapeAttributeValue();
            attributeValue.setAttributeName(e.getKey());
            attributeValue.setValue(e.getValue());
            attributeValue.setShape(entity);
            entity.getAttributes().add(attributeValue);

        });
        return entity;
    }
}
