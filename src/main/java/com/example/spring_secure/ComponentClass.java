package com.example.spring_secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ComponentClass {
    private static final Logger logger = LoggerFactory.getLogger(ComponentClass.class);


    public int divide(int a, int b){

        try {
            int toReturn = a/b;
            logger.info("Division av " + a + " / " + b + " = " + toReturn);
            return a / b;
        } catch (ArithmeticException e) {
            logger.error("Error: du delar på 0");
            return 0;

        }
    }
}
