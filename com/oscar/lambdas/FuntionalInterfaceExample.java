package com.oscar.lambdas;

//Funtional Interface - is an interface that contains a single abstract method (SAM)
//@FunctionalInterface if the interface does not follow the rules for a FI the compiler will give you an error
@FunctionalInterface
public interface FuntionalInterfaceExample{
    boolean sam(Integer i);
    
    default void Saluda()
    {
        System.out.println("Hola soy una interface funcional");
    }

}