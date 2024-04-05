package com.oscar.nestedclasses;
//Local class that does not have a name
//Most extends an existing class or implement an interface
public class AnonymousInnerClass {
    abstract class ClassToExtend{
        abstract String saluda();
    }

    public void method(){
        ClassToExtend anonymousClass = new ClassToExtend(){
          String saluda(){
              return "Hola desde anonimo";
          }
        };

        System.out.println(anonymousClass.saluda());
    }

    public static void main(String[] args) {
        AnonymousInnerClass aIC = new AnonymousInnerClass();
        aIC.method();
    }

}
