package com.oscar.nestedclasses;
//Member level, can be instantiated without an instance of the enclosing class
//Not able to access to instance variables or methods declared in the outer class
public class StaticNestedClass {
    private String nombre="StaticNestedClass";
    static class innerClass{
        public void Saluda(){
            //not allowed to call instance variables
            System.out.println("Static nested class in:"+"StaticNestedClass");
        }
    }

    public static void main(String[] args) {
        //Can be instantiated without an instance of the enclosing class
        innerClass sNC = new innerClass();
        sNC.Saluda();
    }
}
