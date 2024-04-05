package com.oscar.sealedClasses;

//Sealed class is a class that restricts which other classes may directly extend it
//The permits clause is optional in the sealed class and its direct subclasses are declared
//within the same file or the subclasses are nested within the sealed class
public sealed class SealedClass permits NonSealed, SealedClass.SonClass, SunClass {
    final class SonClass extends SealedClass{}

}
