package com.oscar.lambdas;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.function.*;

// Lambdas - allow you to specify code that will be run later in a program
// Functional programming - write code declarative > state of the objects , uses lambdas to write code
// Lambda expression - block of code that gets passed around -> Unnamed method existing inside on anonymous class
// deferred execution - coded is specified now but will run later
public class lambdas {


    public void start() {

       FunctionalInterfaces();
       MethodReferences();
       BuildInFI();
       BuildInFIForPrimitives();
       VariablesInLambdas();

    }



    private void VariablesInLambdas() {
        //They can appear in three places with respect to lambdas
        //The parameter list
        //Local variables inside lambda body
        //Variables referenced from the lambda body

      /*  public void variables(int a)
        {
            int b = 1;
            Predicate<Integer> p1 = a ->{
                int b=0;
                int c=0;
                return b==c;
            }
        }*/

        //Lambda parameters are just like method parameters, you can add modifiers to them
        //Redeclare variables inside is not allowed
        //formats for parameters: (must use the same format)
        // -without types  (x,y)-> x-y;
        // -with types     (Integer x, String y) -> sout(y);
        // -with var       (var x, var y)-> sout(x+y);
        String name ="Oscar Mejia ";

        Consumer<String> consumer = s-> System.out.println(name +s);
        consumer.accept("Medina");

    }

    private void BuildInFIForPrimitives() {
        //Functional Interfaces for primitives
        //Most of them are for double, int, and long types with one exception BooleanSupplier


        //BooleanSupplier
        {
            BooleanSupplier bs1 = () -> Math.random()>.5;
            System.out.println(bs1.getAsBoolean());
        }

        // double, int & long
        //Supplier - no parameters but return a primitive
        {
            DoubleSupplier ds = ()->2.3;
            IntSupplier is = ()->45;
            LongSupplier ls = ()->99;
            System.out.println("double: "+ ds.getAsDouble()+" int: "+is.getAsInt() +" long:  "+ls.getAsLong());

        }
        //Consumer - one parameter return void
        {
            DoubleConsumer dc = x-> System.out.println("double:"+x);
            IntConsumer ic = x-> System.out.println("int:"+x);
            LongConsumer lc = x-> System.out.println("long:"+x);

            dc.accept(12.12);
            ic.accept(123);
            lc.accept(999l);
        }

        //Predicate - one parameter return boolean
        {
            DoublePredicate dp = x-> x>100;
            IntPredicate ip = x-> x>10;
            LongPredicate lp = x-> x>10000;

            System.out.println("1000.40 is greater than 100:"+dp.test(1000.4));
            System.out.println("1000 is greater than 10:"+ip.test(1000));
            System.out.println("1000 is greater than 10000:"+lp.test(1000));
        }

        //Function  - one primitive parameter and return a generic R
        {
            DoubleFunction<Integer> df = x-> 69;
            IntFunction<String> ifunct= x-> "Tienes "+x;
            LongFunction<Long> lf= x-> (long) (x*1.15);

            System.out.println(df.apply(1));
            System.out.println(ifunct.apply(33));
            System.out.println(lf.apply(4050L));
        }

        //UnaryOperator - take one primitive parameter and return same primitive
        {
            DoubleUnaryOperator duo = x-> 1.15 *x;
            IntUnaryOperator iuo = x->x+x;
            LongUnaryOperator luo = x-> x*18;

            System.out.println(duo.applyAsDouble(400.90));
            System.out.println(iuo.applyAsInt(12));
            System.out.println(luo.applyAsLong(1));
        }

        //BinaryOperator - take two primitive and return 1 primitive
        {
            DoubleBinaryOperator dbo = (x,y)->x+y;
            IntBinaryOperator ibo = (x,y)->x+y;
            LongBinaryOperator lbo = (x,y)->x*y;

            System.out.println(dbo.applyAsDouble(9.4,1.6));
            System.out.println(ibo.applyAsInt(1,9));
            System.out.println(lbo.applyAsLong(90000,90000));
        }



    }

    private void BuildInFI() {
        // java.util.function
        // <T> allows the interface to take an object of a specified type.
        // If a second type is required we use the next letter U
        // If a distinct return type is needed,we choose R for return as the generic type


        // Supplier -- to generate or supply values without taking any input
        {
            Supplier<LocalDate> s1 = LocalDate::now;
            //it use the method get()
            LocalDate now = s1.get();
            System.out.println(now);

            Supplier<StringBuilder> s2 = StringBuilder::new;
            //it use the method get()
            StringBuilder sb = s2.get();
            sb.append("Hola");
            System.out.println(sb);
        }

        //Consumer and BiConsumer -- do something with a parameter but not return anything
        //BiConsumer can use the same type for both parameters
        {
            Consumer<String> c1 = System.out::println;
            //it use the method accept return void and take a generic
            c1.accept("Oscar Mejia Medina");

            // Consumer - andThen() - Return a compose Consumer that perform the operation in sequence
            // andThen runs two funcional interfaces in sequence
            Consumer<String> c2 = System.out::println;
            Consumer<String> combined = c1.andThen(c2);
            combined.accept(" Dos ");



            var map= new HashMap<String,Integer>();
            BiConsumer<String,Integer> b1 = (k,v)->map.put(k,v);
            //it use the method accept return void and take a two generics
            b1.accept("Oscar",1);
            c1.accept(map.toString());




        }

        //Predicate and BiPredicate -- used to filtering or matching return a boolean (not a Boolean) and take a Generics
        {
            Predicate<String> p1 = String::isEmpty;
            //it use the method test that return a boolean and take a generic
            System.out.println(p1.test(""));

            BiPredicate<String,String> biPredicate = String::startsWith;
            BiPredicate<String,String> biPredicate2 = (string,prefix)->string.startsWith(prefix);


            System.out.println(biPredicate.test("Oscar","O"));

            //and and negate Methods
            Predicate<String> p2 = s->s.contains("null");
            Predicate<String> compose = p1.negate().and(p2.negate());
            System.out.println(compose.test("Oscar"));
            System.out.println(compose.test("null"));
            System.out.println(compose.test(""));


        }

        //Function and Bifunction -- is responsible for turning one parameter into a value of a potentially  different type and returning
        {
            Function<String,Integer> f1a = x->x.length();
            Function<String,Integer> f1b = String::length;

            //The first two types in th BiFunction are the input types. The third is the result  type.
            BiFunction<String,String,String> buFa = (string,toAdd)-> string.concat(toAdd);
            BiFunction<String,String,String> buFb = String::concat;

            System.out.println(f1a.apply("Anita lava la tina"));
            System.out.println(buFa.apply("Oscar ","Mejia"));


            //Function compose()
            //the compose() method chains functional interfaces and passes along the output of inre to the input of another

            Function<Integer,Integer> sumaUno= x->x+1; //before
            Function<Integer,Integer> multiplicaPorDos= x->x*2; //after
            Function<Integer,Integer> combined= multiplicaPorDos.compose(sumaUno);

            System.out.println(combined.apply(3));




        }

        //UnaryOperator and BinaryOperator -- All the parameters must be the same type
        //UnaryOperator transforms its value into one of the same type
        //BinaryOperator merges two values into one of the same type
        //We do not need to specify the return type in the generics
        {
            UnaryOperator<String> UOa = x-> x.toUpperCase();
            UnaryOperator<String> UOb = String::toUpperCase;

            BinaryOperator<String> BOa = (string,concat)-> string.concat(concat);
            BinaryOperator<String> BOb = String::concat;

            System.out.println(UOa.apply("oscar"));
            System.out.println(BOa.apply("Oscar ","Mejia"));
        }



    }

    private  void FunctionalInterfaces() {
        FuntionalInterfaceExample fi1 = (Integer a)-> {return a>0;};

        //The parentheses around the lambda can be omitted if there is a single parameter and its type is not explicit stated
        FuntionalInterfaceExample fi2 = a-> {return a>0;};

        //We can omit braces when we have only a single statement
        //You can omit return statement and semicolon when no braces are used
        FuntionalInterfaceExample fi3 = a-> {return a>0;};

        System.out.println(fi1.sam(1));
        System.out.println(fi2.sam(-1));
        System.out.println(fi3.sam(23));

    }

    private void MethodReferences() {
        //Method references - way to make code easier to read
        //It is like a lambda and it is used for deferred execution wit a FI



        //There are 4 formats for method references

        //Static Methods
        FunctionalInterface3 fi = Math::round;
        FunctionalInterface3 fi2 = x-> Math.round(x);
        System.out.println(fi.round(100.21));

        //Calling instance methods on an object
        String a ="Oscar Mejia";
        FunctionalInterfaceExample2 fi3 = a::startsWith;
        FunctionalInterfaceExample2 fi4 = x->x.startsWith(x);
        System.out.println(fi3.stringFI("Oscar"));
        System.out.println(fi4.stringFI("Mejia"));

        //Instance methods on a parameter
        FunctionalInterfaceExample2 fi5 = String::isEmpty; //isEmpty is not static
        FunctionalInterfaceExample2 fi6 = x->x.isEmpty();

        System.out.println(fi5.stringFI("HOla"));
        System.out.println(fi5.stringFI(""));

        //Calling constructors
        //-A constructor reference uses new instead of a method and instances an object
        EmptyStringCreator str = String::new;
        EmptyStringCreator str2 = ()-> new String();

        var word = str.create();
        System.out.println(word.concat("Hola"));




    }


}




