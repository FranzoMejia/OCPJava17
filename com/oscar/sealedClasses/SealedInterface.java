package com.oscar.sealedClasses;

//Sealing interfaces permits lists can apply to a class that implements the interface or an interface that extends the interface
//Interfaces that extend a sealed interface con only be marked as sealed or non-sealed. They cannot be marked final
public sealed interface SealedInterface permits SunClass{

}
