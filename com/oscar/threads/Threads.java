package com.oscar.threads;

import com.oscar.interfaces.Chapter;
import com.oscar.streams.Streams;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public class Threads implements Chapter {
    static Runnable printInventory = ()-> System.out.println("Making inventory");
    static Runnable printRecords = ()-> {
        for (int i = 0; i < 3; i++) {
            System.out.println("Printing:"+i);
        }
    };
    @Override
    public void start() {
        //threadsBasics();
        //councurrencyAPI();
        concurrentCollections();


    }

    private void concurrentCollections() {
        //A memory consistency error - is when two threads have inconsistent views of what should be the same data (ConcurrentModificationException)
        //Concurrent classes were created to help avoid common issues which multiple threads add/remove objects for the same collection
        //You should use concurrent when you have multiple threads trying to modify the sema collection

        //The Skip classes are just sorted versions
        //The CopyOnWrite classes creates a copy of the collection any time a reference is added, removed, or changed in the collection and then update the original collection to the new copy


        Map cocurrentMap = new ConcurrentHashMap();

        Queue concurrentQueue = new ConcurrentLinkedQueue();

        ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap();

        ConcurrentSkipListSet concurrentSkipListSet = new ConcurrentSkipListSet();

        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();

        //This class is just like a regular
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();

        //There are multiple methods in Collections to get synchronized versions of a collection
        Collections.synchronizedCollection(new ArrayList<>());
        Collections.synchronizedList(new ArrayList<>());
        Collections.synchronizedMap(new HashMap<>());
        Collections.synchronizedNavigableMap(new TreeMap<>());
        Collections.synchronizedNavigableSet(new TreeSet<>());
        Collections.synchronizedSet(new HashSet<>());
        Collections.synchronizedSortedMap(new TreeMap<>());
        Collections.synchronizedSortedSet(new TreeSet<>());


        //Threading Problems
        //A threading problem can occur when ywo or more threads interact in an unexpected and undesirable way

        //Liveness problem - app becomes unresponsive types:
            //DeadLock - Two or more threads are blocked forever, each waiting on the other
            //Starvation - Single thread is denied access to a shared resource
            //LiveLock - two or more threads are blocked forever, but still active

        //Race conditions - when two task that should be sequentially are completed in the same time

        //Parallel Steams
        //Serial stream - is a stream which results are ordered, whit only one entry being processed at a time
        //Parallel stream - is a capable processing results concurrently, using multiple threads
        Collection<Integer> collection = List.of(2,3,5);

        //The first way to create a parallel stream is from an existing stream
        Stream<Integer> parallelStream = collection.stream().parallel();
        Stream<Integer> parallelStream2 = collection.parallelStream();

        //Parallel decomposition - is the process of taking a task, breaking it into smaller pieces that can be performed concurrently and the reassembling the results
        //Parallel reduction - is a reduction operation applied to a parallel stream

        //Order still preserved
        parallelStream.findFirst().stream().limit(3).skip(2);

        //The reduce() combines a stream into a single object
        System.out.println(List.of('w','o','l','f').parallelStream()
                .reduce("",//identity
                        (s1,c)->s1+c, // accumulator
                        (s2,s3)->s2+s3)); //combiner

        //
        Stream<String> streamString = Stream.of("w","o","l","f");
        SortedSet<String> set  = streamString.collect(
                ConcurrentSkipListSet::new, //supplier
                Set::add, //accumulator
                Set::addAll); //combiner
        System.out.println(set);

    }

    private void councurrencyAPI()  {
        //java.util.concurrent
        //ExecutorService interface, which defines services that create and manage threads
        //The framework includes numerous useful features, such as threads pooling and scheduling


        //Single-Thread Executor
        //Executors factory class that can be used to create instances of the ExecutorService object
        //Single-thread executor, tasks are guaranteed to be executed sequentially
        ExecutorService serviceSingle = Executors.newSingleThreadExecutor();
        try {
            Thread.sleep(2_000);//Waiting for others threads to complete
            System.out.println("_____Single Thread Executor_____");
            serviceSingle.execute(printInventory);
            serviceSingle.execute(printRecords);
            serviceSingle.execute(printInventory);
            System.out.println("++++END++++");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            //it is important that you call the shutdown()
            //it does not stop any tasks that have already been submitted to the thread executor
            //shutdownNow() try to stop all running tasks
            System.out.println(serviceSingle.isShutdown());
            serviceSingle.shutdown();
            //service.shutdownNow();
            System.out.println(serviceSingle.isShutdown());
        }

        //Submitting tasks
        //execute(Runnable) complete task asynchronously is in inherited from the Executor interface, return void
        //submit(Runnable) complete task asynchronously, return Future that can be used to determine whether task is complete
        ExecutorService service = Executors.newFixedThreadPool(2);
        try {
            Future<?> submit = service.submit(printRecords);
            System.out.println(submit.isDone());
            System.out.println(submit.get()); // wait until return is completed
            System.out.println(submit.get(10, TimeUnit.SECONDS)); // wait at most 10 seconds, throwing TimeoutException if not

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            service.shutdown();
        }
        try {
            //wait up to one minute for the results
            service.awaitTermination(5,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        //Callable functional interface
        //Have call() that returns a values and can throw a checked exception
        //The Callable interface is often preferable over Runnable, since it allows more details to be retrieved easily from the task after it is completed

        //Schedule Executor return a ScheduledFuture that is identical to Future + getDelay()
        Runnable task1 = ()->
            System.out.println("Done--Task:"+counter++);

        ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

        //Task is scheduled 10 seconds in the future
        //If the ScheduledExecutorService si shutdown, the pending task will be discarted
        try {
            //Task is scheduled 10 seconds in the future
            scheduledService.schedule(task1, 10, TimeUnit.SECONDS);

            //Task is scheduled every two seconds after 1 second of delay
            scheduledService.scheduleAtFixedRate(task1,1,2,TimeUnit.SECONDS);

            //scheduleWithFixedDelat() create a new task only after previous task finished
            scheduledService.scheduleWithFixedDelay(task1,1,4,TimeUnit.SECONDS);

            scheduledService.awaitTermination(20,TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            scheduledService.shutdown();
        }


        //INCREASING CONCURRENCY WITH POOLS
        //A thread pool is a group of pre-instantiated reusable threads that are available to perform a set of arbitrary tasks
        //A pooled-thread executor can execute the next task concurrently

        //Creates a thread pool, that reuses it when they are available
        ExecutorService servicePool = Executors.newCachedThreadPool();

        //Creates a thread with fixed integer pool, that reuses it when they are available
        ExecutorService serviceFixedPool = Executors.newFixedThreadPool(4);

        //Create a fixed pool of ScheduledExecutorService
        ScheduledExecutorService serviceScheduledPool = Executors.newScheduledThreadPool(4);

        //Thread-Safe Code - property of an object that guarantees safe execution by multiple threads at the same time
        //Race condition - the unexpected result of two tasks executing at the same time
        //volatile keyword is used to guarantee that access to data within memory is consistent
        //volatile ensures that only one thread is modifying a variable at one time

        //private volatile int sheepCount=0;

        //Atomic - is the property of an operation to be carried out as a single unit of execution without any interference from another thread
        //Using atomic classes ensures that the data is consistent between workers and that no values are lost due to concurrent modifications
        AtomicBoolean flag = new AtomicBoolean(true);
        flag.set(false);
        System.out.println("AtomicBoolean: "+flag.get());


        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            System.out.println(count.getAndIncrement());
        }
        AtomicLong atomicLong;

        //Synchronized blocks
        //A monitor, also called a lock, is a structure that supports mutual exclusion, which is the property that at most one thread is executing a particular
        //segment of code at a given time
        //Any Object can be used as a monitor
        //Each thread that arrives will first check if any threads are already running the block
        //They each wait at the synchronized block for the worker to increment and report the result before entering
        Integer lock =0;
        synchronized (lock){
            lock++;
        }

        synchronizedMethod();

        //Using Lock framework
        Lock lock1 = new ReentrantLock();
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            pool.submit(()->sumaWithLock(lock1));
        }
        try {
            pool.awaitTermination(3,TimeUnit.SECONDS);
            pool.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //CyclicBarrier
        //Take it in constructor a limit value, indicating the number of threads to wait for
        //The CyclicBarrier allows us to perform complex, multithreading tasks while all threads stop and wait at logical barriers
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        var servicePool2 = Executors.newFixedThreadPool(2);
        try{
            for (int i = 0; i < 2; i++) {
                servicePool2.submit(()->performTaskWithCyclingBarrier(cyclicBarrier));
            }
        }
        finally {
            servicePool2.shutdown();
        }



    }

    void performTaskWithCyclingBarrier(CyclicBarrier cyclicBarrier)
    {

        try {
            System.out.println("Before");
            cyclicBarrier.await();
            System.out.println("After");
            //After CyclicBarrier reached his limit it goes back to zero
            cyclicBarrier.await();
            System.out.println("After After");
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

    }




    void  sumaWithLock(Lock lock1) {
        //Lock framework - We can lock with only on an object that implements the Lock interface
        //ReentrantLock  is a simple monitor that supports mutual exclusion
        //ensures that once a thread has called lock all others threads will wait until unlock


        try {
            //The tryLock method will attempt to acquire a lock and immediately return a boolean
            //If a lock is available it will immediately return, if not it will wait time limit
            if (lock1.tryLock(1,TimeUnit.SECONDS)) {
            //if (lock1.tryLock()) {
            try {
                //lock1.lock();
                System.out.println("count:" + counter);
                counter = counter + 1;
                //protected code
                //should have a try/finally block
            } finally {
                //it is critical that you release a lock the same number of timer it is adquired
                lock1.unlock();
            }
            }
            else
                System.out.println("lock not adquired");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //We can add the synchronized modifier to any instance method to synchronize automatically on the object itself
    //We can also apply the synchronized modifier to static methods
    synchronized void synchronizedMethod() {
        System.out.println("La La La");
    }

    public static int counter=0;
    private void threadsBasics() {
        //Thread - is the smallest unit of execution that can be scheduled by operating system
        //Process - is a group of associated threads that execute in the same shared environment
        //Single-threaded process - is one that contains exactly one thread
        //Multi-threaded process - support more than one thread
        //Shared environment - the threads share the same memory space and can communicate directly with one another
        //Task - is a single unit of work performed by a thread
        //Concurrency - the property of executing multiple threads and processes at the same time
        //OS thread scheduler - determine which threads should be currently executing
        //Context switch - is the process of storing a thread's state and restoring the state to continue execution
        //thread priority - numeric value(integer) associated to thread execution property


        //Runnable is fi that takes no arguments and returns no data
        //starts() start the thread execution
        //asynchronous - we mean that the thread executing main() does not wait for the results of each new thread created
        //synchronous - the program wait for thread to executing before moving on to the next step
        new Thread(()-> System.out.println("hello")).start();
        System.out.println("World");


        System.out.println("begin");
        new Thread(printInventory).start();
        new Thread(printRecords).start();
        new Thread(printInventory).start();


        Thread daemon= new Thread(printRecords);
        daemon.setDaemon(true);
        System.out.println(daemon.getState());
        daemon.start();
        System.out.println(daemon.getState());
        //Thread Types
        //System thread - is created by the JVM and runs in the background of the application
        //User-defined thread is one created by the app developer to accomplish a specific task
        //Daemon thread - is one that will not prevent the JVM when program finishes
        //getState() to query thread State:
        //NEW - Created but not started
        //RUNNABLE - Running or able to run
        //TERMINATED - Task complete
        //BLOCKED - Waiting to enter synchronized
        //WAITING - Waiting indefinitely to be noticed
        //TIMED_WAITING - Waiting a specified time

        //Once the work is completed or exception the thread becomes TERMINATED

        //Polling - is the process of intermittently checking data at some fixed interval
        final var mainThread=Thread.currentThread();
        new Thread(()->{
            for (int i = 0; i < 1_000_000; i++) {
                counter++;
                mainThread.interrupt();
            }
        }).start();
        while (counter<1_000_000) {
            System.out.println("Not reatched");
            try {
                Thread.sleep(1);

            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED");;
            }
        }
        System.out.println("reatched");

        //Interrupting a Thread
        //Calling interrupt() on a thread in the TIME_WAITING or WAITING states causes the main() triggering a InterruptedException
        //Calling interrupt() on a thread already in a RUNNABLE doesn't change the state

    }
}
