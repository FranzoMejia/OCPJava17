package com.oscar.polymorphism;


import com.oscar.interfaces.AnimalI;
import com.oscar.interfaces.MamiferoI;

// Polymorphism - Is the property of an object to take on many diferent forms:
// - Same Type of object (1)
// - Superclass of the object (2)
// - Interface that implement (3)
public class Polymorphism {

    //When an object has been assigned to new reference type ,only the methods and variables available to that reference
    //type are callable on the object without an explicit cast
    public static void main(String[] args) {

        //The type of the object determines which properties exists within the object memory
        SonClass obj1 = new SonClass();
        obj1.Saluda();
        System.out.println(obj1.name);

        //The type of the reference to the object determines which methods and variables are accessible to the Java program
        //You do not need a cast operator if you are casting to an inhered supertype
        ParentClass obj2 = obj1;
        System.out.println(obj2.name);

        SomeInterface obj3= obj1;
        obj3.Saluda();


        //Casting objects
        //If you want to access a subtype of the current reference, you need to perform a explicit cast
        SonClass obj4 = (SonClass) obj2;

        //The compiler dissallows cast to Unrelated Types
        //obj4 =(Integer)obj2;

        //With casting interfaces the compiles does not detect compilation error but ClassCastException on runtime
        MamiferoI someI = new MamiferoI() {
            @Override
            public void hacerSonido() {

            }

            @Override
            public Number dameTuEdad() {
                return null;
            }
        };

        //java.lang.ClassCastException
        //obj3 = (SomeInterface) someI;

        //The instance of operator can be used to check whether an object belongs to a particular class or interface
        if(someI instanceof SomeInterface S)
            obj3=S;

        //The compiler does not allow instanceof to be used with unrelated types
        //if(someI instanceof Integer)

    }



}

class SonClass extends ParentClass implements SomeInterface{
    public void Saluda(){
        System.out.println("Hola desde son class "+super.name);
    }
}

interface SomeInterface{
    void Saluda();
}

class ParentClass{
    String name="Oscar";
}
