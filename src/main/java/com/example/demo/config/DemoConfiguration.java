package com.example.demo.config;

import com.example.demo.shape.model.ShapeAttribute;
import com.example.demo.shape.model.ShapeCategory;
import com.example.demo.shape.service.ShapeService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DemoConfiguration  implements ApplicationContextAware, InitializingBean {

    final private ShapeService shapeService;

    public DemoConfiguration(ShapeService shapeService) {
        this.shapeService = shapeService;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // triangle
        ShapeCategory shapeCategory = new ShapeCategory();
        shapeCategory.setName("TRIANGLE");
        shapeCategory.setAreaFormula("(sqrt( (a+b+c) * (a+b-c) * (b+c-a) * (c+a-b)))/4");
        shapeCategory.getAttributes().add(new ShapeAttribute("a", shapeCategory));
        shapeCategory.getAttributes().add(new ShapeAttribute("c", shapeCategory));
        shapeCategory.getAttributes().add(new ShapeAttribute("b", shapeCategory));
        this.shapeService.saveCategory(shapeCategory);
        shapeCategory = new ShapeCategory();
        shapeCategory.setName("SQUARE");
        shapeCategory.setAreaFormula("a*a");
        shapeCategory.getAttributes().add(new ShapeAttribute("a", shapeCategory));
        this.shapeService.saveCategory(shapeCategory);

        //rectangle
        shapeCategory = new ShapeCategory();
        shapeCategory.setName("RECTANGLE");
        shapeCategory.setAreaFormula("l*w");
        shapeCategory.getAttributes().add(new ShapeAttribute("l", shapeCategory));
        shapeCategory.getAttributes().add(new ShapeAttribute("w", shapeCategory));
        this.shapeService.saveCategory(shapeCategory);


        shapeCategory = new ShapeCategory();
        shapeCategory.setName("PARALLELOGRAM");
        shapeCategory.setAreaFormula("a*h");
        shapeCategory.getAttributes().add(new ShapeAttribute("a", shapeCategory));
        shapeCategory.getAttributes().add(new ShapeAttribute("h", shapeCategory));
        this.shapeService.saveCategory(shapeCategory);


    }

    @PostConstruct
    public void init(){

    }
}
