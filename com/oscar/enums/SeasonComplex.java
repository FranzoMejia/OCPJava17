package com.oscar.enums;

import com.oscar.interfaces.AnimalI;

//in complex enums we can add
//You can implement interfaces but override the methods
public enum SeasonComplex implements AnimalI
{
    //The list of values always comes first
    WINTER("INVIERNO"){
        public String esp(){ return "INVIERNO";}

        @Override
        void Saluda() {
            System.out.println("Hola desde invierno");
        }
    },
    SUMMER("VERANO"){
        public String esp(){ return "VERANO";}
        @Override
        void Saluda() {
            System.out.println("Hola desde verano");
        }
    },
    SPRING("PRIMAVERA"){
        @Override
        void Saluda() {
            System.out.println("Hola desde invierno");
        }
    },FALL("OTOÑO"){

    @Override
        void Saluda() {
            System.out.println("Hola desde invierno");
        }
    };
    //Always use semicolon ay the end of values

    //Override method from a interface
    @Override
    public void hacerSonido() {

    }
    //Override method from an interface
    @Override
    public Integer dameTuEdad() {
        return null;
    }


    public String spanish;

    //Constructor is implicit public
    //The first time we ask for any enum values Java contruct all
     SeasonComplex(String trad)
    {
        System.out.println("Constructor");
        this.spanish =trad;
    }
    
    //You can add methods and override if needed
    public  String esp(){return "NOT IMPLEMENTED";}


    //When you define an abstract method in a Enum every enum must be implemented it
    abstract void Saluda();



}
