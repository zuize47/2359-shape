package com.example.demo.shape.respository;

import com.example.demo.shape.model.Shape;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShapeRepository extends JpaRepository<Shape, Long> {

    @EntityGraph(attributePaths = "attributes")
    List<Shape> findAllWithAttributeByUser(String user);

    @EntityGraph(attributePaths = "attributes")
    Optional<Shape> findOneWithAttributeById(Long id);
}
