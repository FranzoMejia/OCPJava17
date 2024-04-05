package com.oscar.main;

import com.oscar.collectionsandgenerics.CollectionsPractice;
import com.oscar.enums.SeasonComplex;
import com.oscar.enums.SeasonSimple;
import com.oscar.interfaces.AnimalI;
import com.oscar.lambdas.lambdas;
import com.oscar.streams.Streams;

import java.util.*;
import java.util.logging.Logger;

public class Java17 {
    private static final Logger logger =
            Logger.getLogger(Java17.class.getName());

    public static void main(String[] args) {
        //interfaces();
        //enums();
       //lambdas();
        //usaa();
        //collections();
        streams();

    }

    private static void usaa() {
        //HashSet<String> postingTransactionIDs = new HashSet<String>();
        HashSet<String> postingTransactionIDs = new LinkedHashSet<String>();
        postingTransactionIDs.add("Oscar");
        postingTransactionIDs.add("Oscar");
        postingTransactionIDs.add("Jaz");
        postingTransactionIDs.add("Jaz");
        postingTransactionIDs.add("Cris");

        List<String> postingTransactionIDsNew = new ArrayList<>(postingTransactionIDs);
        System.out.println(postingTransactionIDsNew);


    }

    private static void lambdas() {
        lambdas lam= new lambdas();
        lam.start();
    }

    private static void collections() {
        CollectionsPractice collectionsPractice= new CollectionsPractice();
        collectionsPractice.start();
    }
    private static void streams() {
        Streams streams= new Streams();
        streams.start();
    }



    private static void enums() {
        //You can use equals or == to compare
        Boolean eq = SeasonSimple.SUMMER.equals(SeasonSimple.FALL);
        eq = SeasonSimple.WINTER == SeasonSimple.FALL;

        logger.info(eq.toString());

        //Values method return a Array of values
        logger.info(Arrays.toString(SeasonSimple.values()));

        //name() Method return the name
        Arrays.stream(SeasonSimple.values()).forEach(
                seasonSimple -> logger.info(seasonSimple.name())
        );

        //ordinal Method return the position starting at 0
        Arrays.stream(SeasonSimple.values()).forEach(
                seasonSimple -> System.out.println(seasonSimple.ordinal())
        );

        //Set the value from String if not valid throw InvalidaArgumentException
        SeasonSimple s = SeasonSimple.valueOf("SUMMER");


        //Complex enums
        System.out.println();
        System.out.println(SeasonComplex.WINTER.esp()+
        SeasonComplex.SUMMER.esp()+
        SeasonComplex.FALL.esp()
        );

        //You just use the value
        switch (s){
            case SUMMER -> System.out.println("VERANO");
            case FALL ->  System.out.println("OTOÑO");
            case WINTER ->  System.out.println("INVIERNO");
            case SPRING -> System.out.println("PRIMAVERA");
        }


    }

    private static void interfaces() {
        Perro negro = new Perro();
        negro.hacerSonido();
        logger.info(negro.dameTuEdad().toString());
        AnimalI.darDeComer();
    }
}
