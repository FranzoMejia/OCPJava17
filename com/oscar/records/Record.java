package com.oscar.records;

//Records are special type of data-oriented class, they are final
//it create immutable and encapsulated entities
//Create a constructor for you with the parameters in the same order
//Following are created:
//Constructor
//Access method
//equals()
//hashCode()
//toString()
public class Record {


    public static void main(String[] args) {
        //Every field is inherently final
        //
        Person oscar = new Person("Oscar", 33);
        System.out.println(oscar.toString());
        System.out.println("Nombre:"+oscar.name());
        System.out.println("Edad:"+oscar.edad());
        System.out.println(oscar.getHashCode());
        Person invalid = new Person("noname","apellido", 1);

    }
}

//Records can implement regular or sealed interfaces
//The compiler will not insert a constructor if you define one wth the same parameters in same order
//Records does not support instance initializers
record Person(String name,int edad) {
    //instance fields are not allowed in Records, even if they are private
    //private int factor=9;

    //Compact constructor is a special type of constructor used for records to process validation and transformation
    //Java will execute full constructor after compact constructor
    //Can modify only parameters, not the fields in record
    public Person{
        if(edad<0)
            throw new IllegalArgumentException();
        name = name.toUpperCase();
    }
    //You can also create overloaded constructors that take a completely different list of parameters
    public Person(String nombre,String apellido,int edad)
    {
        //the first line must be an explicit call to another constructor
        this(nombre+apellido,edad);
    }
    public String getHashCode() {
        String ret = this.hashCode()+":"+this.toString();
        return ret;
    }
}
