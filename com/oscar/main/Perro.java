package com.oscar.main;

import com.oscar.interfaces.AnimalI;

import java.util.logging.Logger;

public class Perro implements AnimalI {

    @Override
    public void hacerSonido() {
        logger.info("GUAUUUU");
        logger.info(TASA.toString());
        logger.info(calcularEdad(2,4).toString());
    }

    public Integer dameTuEdad(){
        return 9;
    }

    public static void  Saludar(){
        logger.info("HOLA");
    }
}
