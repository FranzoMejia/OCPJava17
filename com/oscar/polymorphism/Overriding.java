package com.oscar.polymorphism;
// When you override a method you replace ALL CALLS to it, even those defined in the superclass

public class Overriding {

    public static void main(String[] args) {
        Overriding ov = new Overriding();
        EmperorPenguin ep = ov.new EmperorPenguin();
        Penguin pe = ov.new Penguin();

        ep.printInfo();

        System.out.println(pe.original);
        System.out.println(ep.original);



    }
    public  float rest(){
        boolean aFalse = Boolean.FALSE;
        return 3f;
    }

    class Penguin{
        //You can prevent override, making the method final
        public int getHeight(){ return 3;}
        //Variables and static methods cannot be overridden just hiding
        String original="Original content";
        public void printInfo(){
            System.out.println(getHeight());
        }
    }

    class EmperorPenguin extends Penguin{
        //Java uses the reference type to determine which value/version
        String original="Hidding content";
        //If the method is override, then the override version will be used in ALL places
        @Override
        public int getHeight() {
            return 8;
        }
    }


}
