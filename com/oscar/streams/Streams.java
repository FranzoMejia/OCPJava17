package com.oscar.streams;

import com.oscar.interfaces.Chapter;

import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

public class Streams implements Chapter {
    @Override
    public void start() {
        System.out.println("STREAMS");
        //OptionalClass();
        //usingStreams();
        primitiveStreams();
        SpliteratorExample();
    }

    private void SpliteratorExample() {
        //Split the stream in half parts
        List<Integer> list = List.of(6,7,8,9,10);

        Spliterator<Integer> spliterator = list.spliterator();
        var firstHalf = spliterator.trySplit();

        firstHalf.forEachRemaining(System.out::println);
        System.out.println("____________________");
        //tryAdvance proccess next sigle element
        spliterator.tryAdvance(System.out::println);
        System.out.println("____________________");
        spliterator.forEachRemaining(System.out::println);

        //Collecting results
        //Predefined collectors are in the Collectors class rather than the Collector interface
        List<String> listString = List.of("Oscar","Francisco","Mejia");

        System.out.println(listString.stream().collect(Collectors.joining(" ")));
        System.out.println(list.stream().collect(Collectors.averagingInt(x->x)));
        System.out.println(list.stream().collect(Collectors.counting()));
    }

    private void primitiveStreams() {
        //Java includes others stram classes to work with int,double and long


        //IntStream - for int,short,byte and char
        //LongStream - for long
        //DoubleStream - for double and float


        IntStream intStream = IntStream.iterate(0,d->d<100,d->d+2);
        intStream.forEach(System.out::println);

        //average
        intStream = IntStream.iterate(0,d->d<100,d->d+2);
        System.out.println(intStream.average());

        //boxed to make a Stream
        intStream = IntStream.iterate(0,d->d<100,d->d+2);
        intStream.boxed().filter(x->x>90).forEach(System.out::println);

        //max
        intStream = IntStream.iterate(0,d->d<100,d->d+2);
        System.out.println(intStream.max().orElse(-1));

        //min
        intStream = IntStream.iterate(0,d->d<100,d->d+2);
        System.out.println(intStream.max().orElse(-1));

        //range
        //rangeClosed
        IntStream.range(1,10).forEach(System.out::println);//1-9
        IntStream.rangeClosed(1,10).forEach(System.out::println);//1-10

        //sum
        System.out.println(IntStream.rangeClosed(1,10).sum());

        //SummaryStatics
        //sum
        System.out.println(IntStream.rangeClosed(1,10).summaryStatistics());

        //Mapping Streams
        //When returning and object stream the method is mapToObj()
        List<Integer> list = List.of(6,7,8,9,10);

        IntStream mapInt = list.stream().mapToInt(x->x);
        mapInt.forEach(System.out::println);

        DoubleStream mapDouble = list.stream().mapToDouble(x->x+.5);
        mapDouble.forEach(System.out::println);

        LongStream mapLong = list.stream().mapToLong(x->x*500);
        mapLong.forEach(System.out::println);








    }

    private void usingStreams() {
        //A Stream in Java is a sequence of data
        //A stream pipeline consists of the operations that run on a stream to produce a result
        //lazy evaluation - delays execution until necessary

        //Stream pipeline elements:
        //Source: Where the streams come from
        //Intermediate operations: do not run until terminal operation runs
        //Terminal operation: produces a result

        //Finite streams
        Stream<String> emptyStream = Stream.empty();
        Stream<Integer> stream = Stream.of(1,2,3,4,5);
        List<Integer> list = List.of(6,7,8,9,10);
        var streamFromList = list.stream();

        //Infinite streams
        //it will keep producing elements as long as you need them
        Stream<Double> randoms = Stream.generate(Math::random);
        Stream<Integer> oddNumbers = Stream.iterate(1,n->n+2);
        Stream<Integer> oddNumbersUp100 = Stream.iterate(1,n->n<100,n->n+2);

        //TerminalOperation
        //You can perform a terminal operation without any intermediate operations but not the other way
        //Reductions are a special type of terminal operation where all the contents of the stream are combined into a single primitive or Object]

        //Counting
        //for finite streams. in infinite stream it never terminates
        System.out.println(oddNumbersUp100.count());

        //Finding the minimum and maximum
        //min(Comparator) max(Comparator)
        System.out.println(stream.min((a,b)->a-b).orElse(0));
        System.out.println(streamFromList.max((a,b)->a-b).orElse(0));

        //Finding a value
        //findAny() method return a random element when working with parallel streams
        System.out.println(oddNumbers.findAny());
        System.out.println(list.stream().findFirst());

        //Matching
        //All take a predicate as parameter and return boolean
        System.out.println(list.stream().anyMatch(x->x>100));
        System.out.println(list.stream().allMatch(x->x<100));
        System.out.println(list.stream().noneMatch(x->x>100));

        //Iterating
        //It is the only terminal operation with a return type void
        //Streams can not be used as the source in a for-each loop because they do not implement the Iterable interface
        list.stream().forEach(System.out::println);

        //Reducing
        //The reduce method combines a stream into a single object, it processes all elements
        //identity : is the initial value
        //accumulator : combines the current relut with the current calue in the stream

        Integer suma = list.stream().reduce(0,(a,b)->a+b);
        System.out.println(suma);
        //When you do not specify an identity, an Optional is returned
        Optional<Integer> sumaOpt = list.stream().reduce((a,b)->a+b);
        System.out.println(sumaOpt.orElse(0));

        //Collecting
        //The collect method is a special type of reduction called a mutable reduction
        //it is more efficient because we use the same mutable object while accumulating
        Stream<String> streamWolf = Stream.of("W","O","L","F");
        StringBuilder word = streamWolf.collect(
                StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append
        );
        System.out.println(word);
        //supplier : creates the object
        //accumulator : BiConsumer that takes two parameters and not return anything
        //combiner : taking two data collections and mrging them


        //Intermediate Operations __________________________
        //produces a stream as its result

        //Filtering
        list.stream().filter(x->x>7).forEach(System.out::println);

        //Removing duplicates
        //calls equals to determine whether the objects are equivalent
        list.stream().distinct().forEach(System.out::println);

        //Restricting by position
        //skip jump the # of values
        //limit number of elements
        randoms.skip(2).limit(4).forEach(System.out::println);

        //Mapping
        //The map method creates one-to-one mapping from the elements in the stream
        //The return type is the stream that is returned
        list.stream().map(x->isNumber(x)).forEach(System.out::println);

        //The flatMap method takes each element in the stream and makes any element it contains top-level elements in a single stream
        List<Integer> list2 = List.of(3,2,1);
        Stream<List<Integer>>  numbers= Stream.of(list,list2);
        numbers.flatMap(x->x.stream()).forEach(System.out::println);

        //Sorting
        //The sorted method returns a stream with the elements sorted
        //uses natural orderin unless we specify a comparator
        list2.stream().sorted().forEach(System.out::println);
        list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        //Taking a Peek
        //Intermediate version of ForEach
        //Looks at each element that goes through that part of the stream
        var count = list.stream().peek(System.out::println).filter(x->x>8).count();
        System.out.println(count);




    }

    private boolean isNumber(Integer num)
    {
        if(num instanceof Number)
            return true;
        return false;
    }

    private void OptionalClass() {
        //Optional as a box that might have something in it or might instead be empty
        Optional<Integer> minutoPar = Optional.empty();
        Integer minutoActual = LocalDateTime.now().getMinute();
        if(minutoActual%2 ==0)
            minutoPar= Optional.of(minutoActual);


        //get() when it is empty return a exception
        //isPresent() return true or false
        if(minutoPar.isPresent())
            System.out.println(minutoPar.get());

        //ifPresent(Consumer) call the consumer with value
        minutoPar.ifPresent(System.out::println);

        //orElse if false return other parameter
        System.out.println(minutoPar.orElse(0));

        //orElseGet(Supplier) if empty call supplier
        System.out.println(minutoPar.orElseGet(()->50+50));

        //orElseThrow()->throws NoSuchElementException
        try{
        System.out.println(minutoPar.orElseThrow());}
        catch (NoSuchElementException e){
            System.out.println("Minuto impar");
        }

        //orElseThrow(Supplier)->throws Exception call in the Supplier
        try{
        System.out.println(minutoPar.orElseThrow(RuntimeException::new));}
         catch (RuntimeException e){
                System.out.println("Minuto impar");
            }

    }
}
