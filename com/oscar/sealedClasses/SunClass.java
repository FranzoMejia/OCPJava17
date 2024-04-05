package com.oscar.sealedClasses;

//The modifier has to be before the class type
//Sealed class needs to be declared and compiled in the same package as its direct subclasses
//Every class that directly extends a sealed class must specify final, sealed or non-sealed
public final class SunClass extends SealedClass implements SealedInterface {
}

//Non-sealed class is used to open a sealed parent class to potentially unknown sub-classes
non-sealed class NonSealed extends SealedClass{

}

class AnyClass extends NonSealed{

}