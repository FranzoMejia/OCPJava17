package com.oscar.nestedclasses;

//Nested class defined within a method
public class LocalInnerClass {
    String name="LocalInnerClass";

    //The class scope ends when the method ends
    public void method()
    {
        String Inername="InnerClass";
        //Can be declared final or abstract
        //Do not have access modifier
        //Can access final and effectively final local variables
        final class InnerLocalClass{
            public void Saluda(){
                //Have access to all fields and methods of the enclosing class
                System.out.println("Inner Local class in :"+name);
            }
        }
        InnerLocalClass iLC= new InnerLocalClass();
        iLC.Saluda();

    }

    public static void main(String[] args) {
        LocalInnerClass mainClass = new LocalInnerClass();
        mainClass.method();
    }





}
