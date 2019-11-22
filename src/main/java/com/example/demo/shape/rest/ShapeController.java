package com.example.demo.shape.rest;

import com.example.demo.security.SecurityUtils;
import com.example.demo.shape.dto.ShapeCategoryDto;
import com.example.demo.shape.dto.ShapeCategorySimpleDto;
import com.example.demo.shape.dto.ShapeDto;
import com.example.demo.shape.model.Shape;
import com.example.demo.shape.model.ShapeAttribute;
import com.example.demo.shape.model.ShapeCategory;
import com.example.demo.shape.CalcAreaException;
import com.example.demo.shape.service.ShapeService;
import com.example.demo.shape.utils.ShapeCalculatorUtils;
import com.example.demo.shape.utils.ShapeMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shape")
@Slf4j
public class ShapeController {

    final private ShapeService shapeService;

    public ShapeController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @GetMapping("/categories")
    ResponseEntity<List<ShapeCategoryDto>> getAllCategory(){
        List<ShapeCategory> categories =  this.shapeService.getAllCategoryFull();
        return ResponseEntity.ok(categories.stream().map(ShapeMapper.INSTANCE::toDto).collect(Collectors.toList()));
    }


    @GetMapping("/category/{catid}")
    ResponseEntity<ShapeCategoryDto> getCategory(@PathVariable("catid") long id){
        Optional<ShapeCategory>  category = this.shapeService.getCategory(id);
        return category.map(e -> ResponseEntity.ok(ShapeMapper.INSTANCE.toDto(e))).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/category/{catid}")
    ResponseEntity<Void> deleteCategory(@PathVariable("catid") long id){
        if(this.shapeService.deleteCategory(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/category/save")
    ResponseEntity<Long> saveCategory(@Valid @RequestBody ShapeCategoryDto shapeCategoryDto){

        final ShapeCategory category = ShapeMapper.INSTANCE.toEntity(shapeCategoryDto);
        try{
            final ShapeCategory categoryDb = this.shapeService.saveCategory(category);
            return ResponseEntity.ok(categoryDb.getId());
        }catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
}

    @PostMapping("/calc")
    ResponseEntity<Double> calcAreaShape(@Valid @RequestBody ShapeDto shapeDto){
        Optional<ShapeCategory> category = this.shapeService.getCategory(shapeDto.getCatId());
        // check exist and validate

        if(category.isPresent() && validateAttribute(category.get(), shapeDto)){
            Map<String, Double> mapAttribute = new HashMap<>();
            shapeDto.getAttributes().entrySet().forEach(e -> mapAttribute.put(e.getKey(), e.getValue()));
            try {
                return ResponseEntity.ok(ShapeCalculatorUtils.calcArea(category.get().getAreaFormula(),mapAttribute));
            } catch (CalcAreaException e) {
                log.error("calcAreaShape", e);
            }
        }

        return ResponseEntity.badRequest().build();
    }
    @PutMapping("/save")
    ResponseEntity<Long> saveShape(@Valid @RequestBody ShapeDto shapeDto){

        Shape shape = ShapeMapper.INSTANCE.toEntity(shapeDto);
        Optional<ShapeCategory> category = this.shapeService.getCategory(shapeDto.getCatId());
        if(category.isPresent()){
            // Validate formula and attribute
            shape.setCategory(category.get());
            shape.setUser(SecurityUtils.getCurrentUsername().get());
            shape = this.shapeService.save(shape);
            return ResponseEntity.ok(shape.getId());
        }else {
            return ResponseEntity.badRequest().build();
        }

    }
    @GetMapping("/all")
    ResponseEntity<List<ShapeDto>> getAllShape(){
        String currentUser = SecurityUtils.getCurrentUsername().get();

        List<Shape> shapes = this.shapeService.getAllShape(currentUser);
        if(shapes.size() > 0){
            List<ShapeDto> collect = shapes.stream().map(ShapeMapper.INSTANCE::toDto).collect(Collectors.toList());
            return ResponseEntity.ok(collect);
        }else{
            return ResponseEntity.ok(Lists.newArrayList());
        }

    }
    @GetMapping("/{id}")
    ResponseEntity<ShapeDto> getShape(@PathVariable("id") long id){
        Optional<Shape> shape = this.shapeService.getShapeById(id);
        return shape.map(e -> ResponseEntity.ok(ShapeMapper.INSTANCE.toDto(e))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteShape(@PathVariable("id") long id){
        if(this.shapeService.deleteShape(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    private boolean validateAttribute(ShapeCategory category, ShapeDto shapeDto){
        if(category.getAttributes().size() != shapeDto.getAttributes().size()){
            return false;
        }
        Set<String> fname1 = category.getAttributes().stream().map(ShapeAttribute::getAttributeName).collect(Collectors.toSet());
        Set<String> fname2 = shapeDto.getAttributes().keySet();
        return fname1.containsAll(fname2);
    }

}
