package com.example.demo.shape.service

import com.example.demo.shape.model.ShapeAttribute
import com.example.demo.shape.model.ShapeCategory
import com.example.demo.shape.respository.ShapeCategoryRepository
import com.example.demo.shape.respository.ShapeRepository
import com.example.demo.shape.service.impl.ShapeServiceImpl
import spock.lang.Specification

class ShapeServiceSpec extends Specification {

    ShapeService shaperService
    ShapeCategoryRepository shapeCategoryRepository = Mock()
    ShapeRepository shapeRepository = Mock()

    def setup() {
        shaperService = new ShapeServiceImpl(shapeRepository, shapeCategoryRepository, jdbcTemplate)
    }

    def 'should return list of category when call getAllCategory'(){
        List<ShapeCategory> listCategory = []
        listCategory.add(new ShapeCategory(id:1,name: "Cat1"))
        listCategory.add(new ShapeCategory(id:2,name: "Cat2"))

        given:
            shapeCategoryRepository.findAll() >> listCategory
        when:
            def listCategoryDb = shaperService.getAllCategory()
        then:
            listCategory.size() == listCategoryDb.size()
            for(def i = 0; i < listCategory.size(); i++){
                listCategory.get(i).id = listCategoryDb.get(i).id
                listCategory.get(i).name = listCategoryDb.get(i).name
            }
    }

    def 'show save and return a category'(){
        List<ShapeAttribute> attributeList = []
        attributeList.add(new ShapeAttribute(attributeName: "att1"))
        attributeList.add(new ShapeAttribute(attributeName: "PI" ))
        def category = new ShapeCategory(id:1, name: "Cycle", areaFormula: "att1*att1*PI", attributes: attributeList)

        given:
            shapeCategoryRepository.save(_) >> category
        when:
            def categoryDb = shaperService.saveCategory(category)
        then:
            category.id == categoryDb.id
            category.name == categoryDb.name
            category.areaFormula == categoryDb.areaFormula
            def attributeListDb = categoryDb.attributes.toList()
            attributeList.size() ==  attributeListDb.size()
            for(def i = 0; i < attributeList.size(); i++){
                attributeList.get(i).attributeName = attributeListDb.get(i).attributeName
            }
    }



}
