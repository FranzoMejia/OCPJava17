package com.oscar.collectionsandgenerics;

import com.oscar.interfaces.Chapter;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

//A collection is a group of objects contained in a single object
//Collection framework is in java.util
public class CollectionsPractice implements Chapter {
    //There are four main interfaces in Java Collections Frameworks
    //List, Queue , Set and Map

    public void start(){

        listImp();
        setImp();
        queueImp();
        mapImp();
        convertListToArray();
        sortingAndSearchingData();
        genericsAndBounding();
        reviewQuestions();



    }

    private  static  void reviewQuestions() {
        Deque<String> q = new LinkedList<>();
        q.push("Primer");
        q.push("Segundo");
        q.push("Tercer");


        System.out.println(q.pop());
        System.out.println(q.peek());
        System.out.println(q);

        List<Estudiante> salon = new ArrayList<>();
        for (int i = 20; i > 0; i--) {
            Estudiante newStudent = new Estudiante(i+10, "Estudiante "+i,i+20);
            salon.add(newStudent);
        }


        Comparator<Estudiante> comp = Comparator.comparingLong(Estudiante::getEdad);

        Collections.sort(salon,comp);


        HashMap<Integer,Integer> hashMap = new HashMap<>();
        hashMap.put(1,88);
        hashMap.put(2,89);
        hashMap.put(3,100);
        hashMap.put(4,null);

        BinaryOperator<Integer> mayor= (x, y)-> {

            System.out.println("X:"+x);
            System.out.println("Y:"+y);
            if (y>x)
                return y;
            return x;
        };

        BinaryOperator<Integer> nullReturn= (x, y)-> {
            if (y!=x)
                return null;
            return null;
        };

        hashMap.merge(1,100,mayor);
        hashMap.merge(4,100,mayor);
        hashMap.merge(4,100,nullReturn);

        System.out.println(hashMap);

        var arrayList = new ArrayList<>();
        arrayList.add("hola");

        var hashSet = new HashSet();
        hashSet.add(new Object());

        var greetings = new ArrayDeque<String>();
        greetings.offerLast("hello");
        greetings.offerLast("hi");
        greetings.offerFirst("ola");
        greetings.pop();
        greetings.peek();
        while(greetings.peek()!=null)
            System.out.println(greetings.pop());



    }


    private void genericsAndBounding() {
        //GENERICS
        //Allow you to write and use parameterized types <>
        //Behind the scenes the compiler replaces all references to T with Object
        //Type erasure - Remove generics syntax from your code
        //You can not overload a generic method with the same of box
        //When override the return type must be a subtype of class defined in the parent class
        class Box<T>{
            private T content;

            public T getContent() {
                return content;
            }

            public void setContent(T content) {
                this.content = content;
            }

            @Override
            public String toString() {
                return getContent().toString();
            }
        }

        Box<String> boxedObject= new Box<>();
        boxedObject.setContent("Oscar Francisco");
        System.out.println(boxedObject.getContent());

        //Naming conventions
        //E - element
        //K - Map key
        //V - Map value
        //N - Number
        //T - Generic data type
        //S,U,V multiple generic values
        List<String> stringList = new ArrayList<>();
        stringList.add("Oscar");
        stringList.add("Francisco");

        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(4);
        printList(stringList);
        printList(integerList);

        System.out.println(sumar(integerList));

        //Does not compile as String does not extend from Number
        //sumar(stringList);



        List<Object> objectList = new ArrayList<>();
        objectList.add("Oscar");
        printListLower(stringList);
        printListLower(objectList);
        //Does not compile as Integer is not a subclass of String
        //printListLower(integerList);

        System.out.println((Integer) firstelem(integerList));






    }
    //UNBOUNDED WILDCARD
    //You use ? when you want to specify any type is okay
    //List of whatever
    private void printList(List<?> list){
        list.forEach(System.out::println);
    }

    //UPPER-BOUNDED WILDCARD
    //Any class that extend a class is okay
    //Any class that extend from a Number
    private Integer sumar(List<? extends Number> list){
        int suma = 0;
        for(Number number:list)
            suma +=number.intValue();
        return suma;
    }

    //LOWER-BOUNDED WILDCARD
    //List of objects that are superclass of some class
    private void printListLower(List<? super String> list){
        list.forEach(System.out::println);
    }

    //Method return generic
     <T> T firstelem(List<?> list){
        return (T) list.get(0);
     }

    private void sortingAndSearchingData() {
        //SORTING DATA
        //For String objects is defined according UNICODE (NUMBER-UPPERCASE-LOWECASE)
        List<Estudiante> salon = new ArrayList<>();
        for (int i = 20; i > 0; i--) {
            Estudiante newStudent = new Estudiante(i+10, "Estudiante "+i,i+20);
            salon.add(newStudent);
        }

        System.out.println(salon);
        Collections.sort(salon); //Needs to implement the Comparable interface
        System.out.println(salon);

        //When you need to order objects in a different way you can use the Comparator SAM
        //You can declare using lambda
        //    0 - first object equals to the second
        //    Positive - first object is larger than second
        //    Negative - first smaller is larger than second

        Comparator<Estudiante> byAgeDesc = (o1, o2) -> o2.getEdad()-o1.getEdad();
        //The method to implement is compare(obj1,obj2)
        Comparator<Estudiante> byAgeAsc = new Comparator<>() {
            @Override
            public int compare(Estudiante o1, Estudiante o2) {
                return o2.getEdad()-o1.getEdad();
            }
        };

        salon.sort(byAgeDesc); //Needs to implement the Comparable interface
        System.out.println(salon);

        salon.sort(byAgeAsc); //Needs to implement the Comparable interface
        System.out.println(salon);

        //SEARCHING DATA
        //BINARY SEARCH MUST BE ORDER IN ASCENDING ORDER

        Estudiante toSearch = new Estudiante(11, null,null);
        Estudiante toSearchNotFound = new Estudiante(599, null,null);
        Collections.sort(salon);
        System.out.println(Collections.binarySearch(salon,toSearch)); //It returns the index of the object
        System.out.println(Collections.binarySearch(salon,toSearchNotFound));//It returns one less than the negated index of the requested value would need to be inserted
    }

    private void convertListToArray() {
        LinkedList<String> filaTortillas = new LinkedList<>();

        filaTortillas.addFirst("Lupe");
        filaTortillas.addLast("Sara");
        filaTortillas.addLast("Lili");
        //CONVERTING LIST -> ARRAY
        //The array is a new object with no relation with the original list
        //toArrays by default converto to an array of objects
        Object[] objArray = filaTortillas.toArray();
        //(new String[0]) java will create a new array with the proper size
        String[] stringArray = filaTortillas.toArray(new String[0]);

        //(new String[0]) java will create a new array size +10
        String[] stringArray10 = filaTortillas.toArray(new String[10]);

    }

    //Map does not extend from Collection interface
    //Maps keys to values, use when you need to identify values by key
    //We need generic type key for Key or Value
    private void mapImp() {
        //HashMap stores the keys in a HashTable
        //Adding and retrieving both have constant time
        //Lose the order
        HashMap<Integer,String> hashMap = new HashMap<>();
        hashMap.put(1,"Oscar");
        hashMap.putIfAbsent(1,"Luis"); //not added because the key already exists
        hashMap.replace(1,"Nuevo valor"); //replace the value of the key
        hashMap.put(2,"Fernando");
        hashMap.put(null,"Pedro");
        hashMap.put(null,"Pedro Juan");//
        hashMap.replaceAll((x,y)-> y +" proceso");
        Collection<String> values = hashMap.values();
        System.out.println(values);

        //TreeMap stores the keys in sorted structure
        //Benefit is ordered
        //Add, check take longer as the tree grows
        //Does not allow null keys
        TreeMap<Integer,String> treeMap = new TreeMap<>();
        treeMap.put(1,"Oscar");
        treeMap.put(2,"Fer");
        treeMap.put(3,"Francisco");
        treeMap.put(90,"Ultimo");
        treeMap.put(4,"Rama");
        System.out.println(treeMap);
        System.out.println(treeMap.size());

        treeMap.clear();



    }

    private void queueImp() {
        //Queue(FIFO)*fila orders its elements in specific order for processing
        //Good when elements are added and removed un specific order
        LinkedList<String> linkedList = new LinkedList<>(); //when you need List methods
        Deque<String> procesos = new LinkedList<>(); // LinkedList also implement deque
        ArrayDeque<String> arrayDeque = new ArrayDeque<>();

        //methods that return an Exception when something goes wrong
        linkedList.add("YO");
        String front = linkedList.element();//Read from the front
        System.out.println(front);
        front = linkedList.remove();//Get and then remove
        //linkedList.remove();  //NoSuchElementException because is empty

        //methods that use the return type
        linkedList.offer("Otro");
        front = linkedList.peek();
        front = linkedList.poll();
        front = linkedList.poll(); //Assign null to front because the list is empty



        //methods that return an Exception when something goes wrong
        procesos.addFirst("Proceso 1");
        procesos.addFirst("Proceso 1");//Allow duplicates
        procesos.addLast("Ultimo");
        System.out.println(procesos.getFirst());
        System.out.println(procesos.getLast());
        System.out.println(procesos.removeFirst());
        System.out.println(procesos.removeLast());
        System.out.println(procesos.size());


        //Methods that use the return type
        procesos.offerFirst("Primero"); //return boolean
        procesos.offerLast("Cierre");
        System.out.println(procesos.peekFirst());// get the first
        System.out.println(procesos.peekLast());// get the last one
        System.out.println(procesos.pollFirst());// get the first and remove
        System.out.println(procesos.pollLast());// get the last and remove

        //Stack
        arrayDeque.push("Uno");
        arrayDeque.push("Dos");
        arrayDeque.push("Tres");
        arrayDeque.push("Cuatro");

        System.out.println(arrayDeque.peek()); //Get the last in the stack
        System.out.println(arrayDeque.pop());  //Ger and remove the last in the stack
        System.out.println(arrayDeque);
        arrayDeque.clear();

    }

    //Set
    //Collection that does not allow duplicate entries
    //
    private void setImp() {
        //HashSet - stores its elements in a hashtable, keys are hash and the values the object itself
        //The main benefif is add/check both have constant time
        //You lose the order in which you inseter
        Set<String> semanaDias =  Set.of("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo");
        //semanaDias.add("Lunes"); //Inmutable Object will throw UnsupportedOperationException
        //
        // semanaDias.add(null);//Inmutable Object will throw UnsupportedOperationException

        Set<String> uniqueValues = new HashSet<>();
        uniqueValues.add("MEMO900525");
        uniqueValues.add("JICA941225");
        uniqueValues.add("MEMO900525"); //no stored because already exists
        uniqueValues.add(null); //allow one null value
        uniqueValues.add(null); //not inserted because already exists
        System.out.println(uniqueValues.size());

        //TreeSet  -  Stores the elements in a tree structure
        // The main benefit is always in sorted order
        //  Add/check take longer than HashSet
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(9);
        treeSet.add(8);
        treeSet.add(1);
        treeSet.add(0);
        //treeSet.add(null); //Does not allow null values NullPointerException
        treeSet.clear();



    }

    //List
    //Ordered collection that allow duplicate entries
    //it is based on an int index #
    private void listImp() {

        //Resizable array
        //look up elements in constant time
        //add or remove is slower than accessing
       // List<Object> listGeneric = new ArrayList<>();// empty list
        //Arrays.asList("Chivas") the list still linkend to the array so it can be modify but not resize(add,remove)
        List<String> listWinners = new ArrayList<>();
        listWinners.add("Chivas");
        listWinners.add("Chivas");
        listWinners.add("Cruz Azul");
        System.out.println(listWinners.size());
        //cointains method call equals
        System.out.println(listWinners.contains("Chivas"));



        //List.of and List.copyOf will create inmutable lists not linked to original
        List<String> listWinnersInmutable = List.copyOf(listWinners);
        //listWinnersInmutable.add("Loco"); // will throw UnsupportedOperationException

        listWinners.add("Puebla"); //Add the E at the end of the list
        listWinners.add(0,"Campeonisimo"); // add the element at index and move to the right the remainig elements
        System.out.println(listWinners.get(listWinners.size()-1)); // Get the element by de index NO.

        System.out.println(listWinners.remove("Chivas"));// remove the first entry of the Object
        listWinners.replaceAll(x->x+" Campeon");//Replace each element with the result of the FI
        System.out.println(listWinners.set(0,"Campeonisimo"));// Replace and return the original


        //Can add,access,remove from the beginning and end of the list in constant time
        //Good choice when using it as Deque
        //takes linear time when using with arbitrary index
        LinkedList<String> filaTortillas = new LinkedList<>();

        filaTortillas.addFirst("Lupe");
        filaTortillas.addLast("Sara");
        filaTortillas.addLast("Lili");
        System.out.println(filaTortillas.removeFirst());
        System.out.println(filaTortillas.removeLast());
        filaTortillas.addFirst("VIP");
        System.out.println(filaTortillas);

    }
}
