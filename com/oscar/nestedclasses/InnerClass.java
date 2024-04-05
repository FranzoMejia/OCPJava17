package com.oscar.nestedclasses;

//Non-static type defined at the member level of a class same as methods, instance variables, and constructors
public class InnerClass {
    //INNER CLASS
    //can be public, protected, package or private
    //can extend a class and implement interfaces
    //can be abstract or final

    String nombre ="InnerClass";
    public class InClass{
        public void Saluda(){
            //Can access members of the outer class, include private members
            System.out.println("In Class into:"+nombre);
        }
    }

    public static void main(String[] args) {
        InnerClass mainClass = new InnerClass();
        InClass inclass = mainClass.new InClass();
        inclass.Saluda();

        //other way to instantiate inner class
        new InnerClass().new InClass().Saluda();
    }

}
