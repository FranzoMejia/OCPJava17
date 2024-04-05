package com.oscar.interfaces;

import com.oscar.main.Java17;
import com.oscar.main.Perro;

import java.util.logging.Logger;

//Always considered to be abstract, cannot be final , can extend multiple interfaces
//Simply define a set of rules and methods that a class implementing them must follow
//Interfaces cannot implement others interfaces
public interface AnimalI {
    Logger logger = Logger.getLogger(Java17.class.getName());
    //Assumed to be public, static and final
    Integer TASA =7;

    //abstract method implicit public
      void hacerSonido();

      //you can implement this method returning any subclass of Number(FLoat, Integer, Long, Float etc)
      Number dameTuEdad();

    //Implicit public, cannot be abstract, final or static, may be overridden by a class, if a class inherits same method name must be overridden the method
    //When a class a conflict by default methods, but we want to access AnimalI.super.calcularEdad(1,2);
     default Integer calcularEdad(int tasa,int edad){
         return tasa*edad;
     }
    //private static method
    //it can be accessed from any method
     private static void come(){
         logger.info("Comiendo");
     }
     //private method
    //it is only accessible from non-static members
    private  void saluda(){
        logger.info("Holla");
    }

    //implicit public
    //cannot be abstract or final
    //Must be called by the Interface Name AnimalI.darDeComer();
    //It is not inherited
     static void darDeComer(){
         come();
     }




}
