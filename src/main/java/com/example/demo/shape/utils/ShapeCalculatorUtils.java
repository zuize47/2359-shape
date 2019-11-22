package com.example.demo.shape.utils;

import com.example.demo.shape.CalcAreaException;
import lombok.extern.slf4j.Slf4j;
import org.cheffo.jeplite.JEP;
import org.cheffo.jeplite.ParseException;

import java.util.Map;

@Slf4j
public class ShapeCalculatorUtils {

    public static double calcArea(String formula, Map<String, Double> values) throws CalcAreaException {
        JEP jep = new JEP();
        jep.addStandardFunctions();
        for (Map.Entry<String, Double> me : values.entrySet()) {
            jep.addVariable(me.getKey(), Double.valueOf(me.getValue()));
        }
        try{
            jep.parseExpression(formula);
            return jep.getValue();
        }catch (ParseException ex){
            log.error("calc Area:", ex);
            throw new CalcAreaException(ex.getMessage());
        }

    }
}
