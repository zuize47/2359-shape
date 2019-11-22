package com.example.demo.shape.service.impl;

import com.example.demo.shape.model.Shape;
import com.example.demo.shape.model.ShapeCategory;
import com.example.demo.shape.respository.ShapeCategoryRepository;
import com.example.demo.shape.respository.ShapeRepository;
import com.example.demo.shape.service.ShapeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ShapeServiceImpl implements ShapeService {

    final private ShapeRepository shapeRepository;
    final private ShapeCategoryRepository shapeCategoryRepository;
    final private JdbcTemplate jdbcTemplate;

    public ShapeServiceImpl(ShapeRepository shapeRepository, ShapeCategoryRepository shapeCategoryRepository, JdbcTemplate jdbcTemplate) {
        this.shapeRepository = shapeRepository;
        this.shapeCategoryRepository = shapeCategoryRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<ShapeCategory> getAllCategory() {
        return shapeCategoryRepository.findAll();
    }

    @Override
    public List<ShapeCategory> getAllCategoryFull() {
        return shapeCategoryRepository.findAll();
    }

    @Override
    public ShapeCategory saveCategory(ShapeCategory category) {
        return shapeCategoryRepository.save(category);
    }

    @Override
    public List<Shape> getAllShape() {
        return this.shapeRepository.findAll();
    }

    @Override
    public List<Shape> getAllShape(String user) {
        return this.shapeRepository.findAllWithAttributeByUser(user);
    }

    @Override
    public Optional<Shape> getShapeById(long id) {
        return this.shapeRepository.findOneWithAttributeById(id);
    }

    @Override
    public Optional<ShapeCategory> getCategory(long id) {
        return shapeCategoryRepository.findOneWithAttributeById(id);
    }

    @Override
    public Shape save(Shape shape) {
        return this.shapeRepository.save(shape);
    }

    @Override
    public boolean deleteShape(long id) {
        return this.shapeRepository.findOneWithAttributeById(id).map(e -> {
            log.info("delete");
            jdbcTemplate.batchUpdate("delete SHAPE_ATTRIBUTES_VALUE where SHAPE_ID=" + id, "delete from SHAPE where ID=" + id);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean deleteCategory(long id) {
        return this.shapeCategoryRepository.findById(id).map(e -> {
            log.info("delete category");
            jdbcTemplate.batchUpdate("delete SHAPE_ATTRIBUTES where CATEGORY_ID=" + id, "delete from SHAPE_CATEGORY  where ID=" + id);
            return true;
        }).orElse(false);
    }
}
