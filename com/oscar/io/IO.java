package com.oscar.io;

import com.oscar.interfaces.Chapter;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class IO implements Chapter {
    //IO - input output
    //NIO.2 - non-blocking I/O API
    //File within the storage device holds data
    //Directory - is a location that can contain files as well as other directories
    //File System - Is in charge of reading and writing data within a computer
    //Root directory is the topmost directory in the file system: Windows (C:\) Linux (/)
    //Path - is a representation of a file or directory within a file system
    //Absolute Path - full path from the root directory
    //Relative Path - from current working dir to the file or directory
    //Path symbol - Is one of a reserved series of characters with special meaning in some file system
        // . to the current directory
        // .. to the parent directory
    //Symbolic link - Is a special file within a file system that serves as a reference or point to another file or directory
        //I/O apis does not support symbolic links
        //NIO.2 full support for symbolic links
    //I/O java.io.File
    //NIO.2 java.nio.Path

    static File testFile =new File("homeFile");
    static Path testPath = Path.of("homePath");

    @Override
    public void start() {
        System.out.println("I/O API4");



        try {
            deleteOnStartup(testPath);

            if(!Files.exists(testPath)) {
                Files.createDirectory(testPath);
            }
            if(!testFile.exists()) {
                Files.createDirectory(testFile.toPath());
            }

            createFilePath();
            operatingFile();
            operatingPath();
            resolvingRelativizePath();
            normalizingPath();
            //directories();
            //IOStreams();
            serialization();
            interactingWithUsers();
            workingWithAdvancedAPIs();
            attributeAndViewsTypes();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void attributeAndViewsTypes() {
        //File attributes provide metadata about a file or directory
        //Basic file attributes - creation time,last access time,last modified time,size,is directory,is regular file,is symbolic link,owner
        //Dos file attributes - read only,hidden,system,archive
        //Posix file attributes - permissions(owner,group,others),owner,group
        //User defined file attributes - custom metadata defined by the user

        //File attribute views provide a way to read and write file attributes in a type-safe manner
        //BasicFileAttributeView - view for basic file attributes
        //DosFileAttributeView - view for dos file attributes
        //PosixFileAttributeView - view for posix file attributes
        //AclFileAttributeView - view for access control list (ACL) file attributes
        //UserDefinedFileAttributeView - view for user defined file attributes

        Path path = Path.of("homePath","testDir","file.txt");
        try {
            if(!Files.exists(path.getParent()))
                Files.createDirectories(path.getParent());
            if(!Files.exists(path))
                Files.createFile(path);

            BasicFileAttributes basicAttr = Files.readAttributes(path,BasicFileAttributes.class);
            System.out.println("Creation time: "+basicAttr.creationTime());
            System.out.println("Last access time: "+basicAttr.lastAccessTime());
            System.out.println("Last modified time: "+basicAttr.lastModifiedTime());
            System.out.println("Is directory: "+basicAttr.isDirectory());
            System.out.println("Is regular file: "+basicAttr.isRegularFile());
            System.out.println("Is symbolic link: "+basicAttr.isSymbolicLink());
            System.out.println("Size: "+basicAttr.size());


            BasicFileAttributeView basicView = Files.getFileAttributeView(path,BasicFileAttributeView.class);
            basicView.setTimes(FileTime.fromMillis(System.currentTimeMillis()),null,null);

            DosFileAttributes dosAttr = Files.readAttributes(path,DosFileAttributes.class);
            System.out.println("Read only: "+dosAttr.isReadOnly());
            System.out.println("Hidden: "+dosAttr.isHidden());
            System.out.println("System: "+dosAttr.isSystem());
            System.out.println("Archive: "+dosAttr.isArchive());

            DosFileAttributeView dosView = Files.getFileAttributeView(path,DosFileAttributeView.class);
            dosView.setReadOnly(true);
            dosView.setHidden(true);

            //For Unix/Linux/Mac

           /* PosixFileAttributes posixAttr = Files.readAttributes(path,PosixFileAttributes.class);
            System.out.println("Owner: "+posixAttr.owner().getName());
            System.out.println("Group: "+posixAttr.group().getName());
            System.out.println("Permissions: "+ PosixFilePermissions.toString(posixAttr.permissions()));
            PosixFileAttributeView posixView = Files.getFileAttributeView(path,PosixFileAttributeView.class);
            posixView.setPermissions(PosixFilePermissions.fromString("rwxr-----"));*/

    } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }

        private void workingWithAdvancedAPIs() throws IOException {
        //Manipulating Input Streams

        InputStream inputStream = InputStream.nullInputStream();
        //serialVersionUID is a unique identifier for each class that is used during deserialization to verify that the sender and receiver of a serialized object have loaded classes for that object that are compatible with respect to serialization
        Path serPath = Path.of("serFile.ser");
        //ObjectOutputStream - is used to write objects to a stream
        //ObjectInputStream - is used to read objects from a stream
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serPath.toFile()));
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serPath.toFile()))
        ){
            if(ois.markSupported()){
                //mark the current position in the stream
                ois.mark(100);
                //reset the stream to the marked position
                ois.reset();
                //skip n bytes in the stream
                ois.skip(10);

            }
        }

    }

    private void interactingWithUsers() throws IOException {
        //System.in returns InputStream that reads from the console
         var reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your name: ");
            //String input =reader.readLine();
            System.out.println("Hello ");




        //Console class is singleton that provides methods to access the console
        //It is not available in all environments
        //only one instance of the Console class can exist at a time

        //IntelliJ run without console support
        Console console = System.console();
        System.out.println("Enter your user: ");
        //String consoleInput = console.readLine();
        System.out.println("Enter your password: ");
        //char[] consoleInputPass = console.readPassword();
        //console.writer().println("Hello from console "+input);
    }

    private void serialization() {
        //Serialization is the process of converting an object into a stream of bytes for storage or transmission
        //Deserialization is the process of converting a stream of bytes back into an object
        //Java provides built-in support for serialization through the java.io.Serializable interface
        //Serializable is a marker interface that indicates that a class can be serialized (not methods to implement)
        //transient keyword is used to indicate that a field should not be serialized
        //It reverts to the default value when deserialized
        //Only the instance fields of the object are serialized, not the static fields

        //serialVersionUID is a unique identifier for each class that is used during deserialization to verify that the sender and receiver of a serialized object have loaded classes for that object that are compatible with respect to serialization
        Path serPath = Path.of("serFile.ser");
        //ObjectOutputStream - is used to write objects to a stream
        //ObjectInputStream - is used to read objects from a stream
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serPath.toFile()));
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serPath.toFile()))
        ){
            //Serialization
            List<Employee> employeeList = new ArrayList<>();
            Employee emp = new Employee("Oscar",35,java.time.LocalDateTime.now());
            employeeList.add(emp);

            emp = new Employee("Oscar",31,java.time.LocalDateTime.now());
            employeeList.add(emp);

            emp = new Employee("Oscar",32,java.time.LocalDateTime.now());
            employeeList.add(emp);

            oos.writeObject(employeeList);
            //Deserialization
            //When you deserialize an object the constructor is not called
            //Java call the non-argument constructor of the first non-serializable superclass
            List<Employee> emp2 = (List<Employee>) ois.readObject();

            emp2.stream().forEach(System.out::println);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void IOStreams() throws IOException {
        //The content of a file can be read or written using I/O streams which is a list of data elements presented in a specific order
        //I/O streams allows the app to focus on only small chunks of data at a time

        //Byte I/O streams - read and write binary data (images,audio,video) and have class names that end with InputStream or OutputStream
        //Most InputStream have a corresponding OutputStream class(FileInputStream and FileOutputStream)(FileReader and FileWriter)

        //Character I/O streams - read and write text data and have class names that end with Reader or Writer
        //Low level streams - directly read or write bytes or characters to a file
        //High level streams - wrap around low level streams to provide additional functionality like buffering or data conversion

        //Wrapping is the process by which an instance is passed as an argument to the constructor of another stream class

        //FileReader is the low level I/O stream whereas BufferedReader is the high level I/O stream that wraps around FileReader.
        //ObjectInputStream allows us to interpret the data as a Java object

        //Tha java.io library defines four abstract classes that are the parent of all I/O stream classes

        //InputStream - abstract class for reading binary data
        //OutputStream - abstract class for writing binary data
        //Reader - abstract class for reading text data
        //Writer - abstract class for writing text data

        //Concrete I/O stream classes
        //FileInputStream - low level byte stream for reading binary data from a file
        //FileOutputStream - low level byte stream for writing binary data to a file
        //FileReader - low level character stream for reading text data from a file
        //FileWriter - low level character stream for writing text data to a file
        //BufferedInputStream - high level byte stream that wraps around another InputStream to provide buffering
        //BufferedOutputStream - high level byte stream that wraps around another OutputStream to provide buffering
        //BufferedReader - high level character stream that wraps around another Reader to provide buffering
        //BufferedWriter - high level character stream that wraps around another Writer to provide buffering
        //ObjectInputStream - high level byte stream that can read Java objects from a binary file
        //ObjectOutputStream - high level byte stream that can write Java objects to a binary file
        //PrintStream - high level byte stream that provides convenient methods for printing formatted representations of objects to a binary file
        //PrintWriter - high level character stream that provides convenient methods for printing formatted representations of objects to a text file

        //Reading and writing data
        //InputStream and Reader declare read() methods for reading data
        //OutputStream and Writer declare write() methods for writing data
        //-1 indicates the end of the stream
        //An offset is the position within the array where the data will be read or written
        //An length is the number of bytes or characters to read or write
        //The flush() method is used to force any buffered data to be written to the underlying stream

        //The character encoding determines how characters are encoded and stored in bytes in a I/O streams and later read back or decoded as characters

        Path textFile = Path.of("textFile.txt");
        if(!Files.exists(textFile)){
            Files.createFile(textFile);
            //This replaces the content of the file
            Files.writeString(textFile,"Hello World");

        }
        byte[] bytearray = new byte[10];
        FileInputStream fileInputStream = new FileInputStream(textFile.toFile());
        //All InputStream and Reader classes have three overloaded read() methods
        int byteRead = fileInputStream.read();
        //Reads up to b.length bytes of data from the input stream into an array of bytes
        byteRead = fileInputStream.read(bytearray);
        //Reads up to len bytes of data from the input stream into an array of bytes starting at offset off
        byteRead = fileInputStream.read(bytearray,0,10);

        System.out.println(Arrays.toString(bytearray));
        while(byteRead != -1){
            System.out.print((char)byteRead);
            byteRead = fileInputStream.read();
        }

        FileReader fileReader = new FileReader(textFile.toFile());
        int charRead = fileReader.read();
        System.out.println((char)charRead);
        char[] charArray = new char[10];
        charRead = fileReader.read(charArray);
        System.out.println(Arrays.toString(charArray));
        charRead = fileReader.read(charArray,5,5);
        System.out.println(Arrays.toString(charArray));


        Path textFile2 = Path.of("textFile2.txt");
        if(!Files.exists(textFile2)){
            Files.createFile(textFile2);
            //This replaces the content of the file
            Files.writeString(textFile2,"Hello World");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(textFile2.toFile());
        //All OutputStream and Writer classes have three overloaded write() methods
        //Writes the specified byte to this output stream
        fileOutputStream.write('H');
        //Writes b.length bytes from the specified byte array to this output stream
        fileOutputStream.write(bytearray);
        //Writes len bytes from the specified byte array starting at offset off to this output stream
        fileOutputStream.write(bytearray,5,5);

        //BufferedInputStream and BufferedReader are high level I/O streams that wrap around low level I/O streams to provide buffering
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        byte[] bytes1 = bufferedInputStream.readAllBytes();
        System.out.println(Arrays.toString(bytes1));

        System.out.println(bufferedReader.readLine());

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(textFile2.toFile()));
        bufferedWriter.write("Hello World BufferedWriter");
        bufferedWriter.newLine();

        //Common Files NIO/2 read and write methods
        //The Files class provides several static methods for reading and writing files
        //These methods are convenient because they handle opening and closing the file automatically
        //They also provide options for reading and writing files in different ways
        //The methods readString() and writeString() are used for reading and writing text files



        //This appends the content to the file
        Files.writeString(textFile, "\nWelcome to Java", StandardOpenOption.APPEND);

        String l = Files.readString(textFile);
        System.out.println(l);

        //Reading the entire content of a file as a String
        String content = Files.readString(textFile);
        System.out.println(content);

        //Reading all lines of a file
        List<String> lines = Files.readAllLines(textFile);
        System.out.println(lines.get(lines.size()-1));

        //lazyly reading lines of a file using Stream
        Stream<String> linesStream = Files.lines(textFile);

        System.out.println(linesStream.filter(x->x.contains("Java")).count());

        //Reading as byte array
        byte[] bytes = Files.readAllBytes(textFile);
        Files.write(textFile, bytes, StandardOpenOption.WRITE);

        //Reading all lines lazyly
        Stream<String> streamLines = Files.lines(textFile);
        System.out.println(streamLines.count());

    }

    private void deleteOnStartup(Path testPath) throws IOException {

        //walk method returns a Stream that is lazily populated with Path by recursively walking the file tree rooted at a given starting file
        Files.find(testPath, Integer.MAX_VALUE,
                (path, basicFileAttributes) -> basicFileAttributes.isRegularFile() &&
                        path.toString().endsWith(".txt"))
                .forEach(System.out::println);
        if(Files.exists(testPath)){
            try {
                Files.walk(testPath)
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(testFile.exists()){
            for(File file : testFile.listFiles()){
                file.delete();
            }
            testFile.delete();
        }
        testFile.delete();
        testPath.toFile().delete();
    }

    private void directories() throws IOException {
        Path path = Path.of("homePath","testDir");
        //createDirectory() method creates a directory at the specified path
        Files.createDirectory(path);
        //createDirectories() method creates a directory and all its parent directories if they do not exist
        Files.createDirectories(Path.of("homePath","testDir","subDir"));

        //Copying files
        //The method shallows copies file from a source path to a target path
        //Shallow copy means that files and subdirectories are not copied
        //Deep copy means that files and subdirectories are copied
        Path sourcePath = Path.of("homePath","testDir","subDir","source.txt");
        Files.createDirectories(sourcePath);
        Path targetPath = Path.of("homePath","testDir","subDir","target.txt");

        //If the target file exists, by default, the copy operation will throw a FileAlreadyExistsException
        Files.copy(sourcePath, targetPath);

        //Moving files or directories from one path to another
        //Atomic move means that the move operation is performed as a single operation
        if(Files.exists(targetPath))
            Files.delete(targetPath);
        Files.move(sourcePath,targetPath, StandardCopyOption.ATOMIC_MOVE);

        //To delete a directory it must be empty
        Files.delete(targetPath);

        //To check if two paths point to the same file use isSameFile() method
        //it resolve all path symbols and symbolic links before making the comparison
        Files.isSameFile(path,testPath);

        //Compare the content of two files use the method Files.mismatch()
        if(Files.exists(sourcePath) && Files.exists(targetPath))
            Files.mismatch(path,targetPath);
        //If the files are identical it returns -1 otherwise it returns the position of the first byte that does not match


    }

    private void normalizingPath() {
        //normalize() method is used to remove redundant elements from a path
        //.. refers to the parent directory
        //. refers to the current directory
        // The equals() method return true if the two paths represent the same file or directory
        // .toRealPath() method resolves the path to an absolute path and removes symbolic links
        // will throw an exception if the path does not exist
        Path path = Path.of("homePath","..","homePath","test.txt");
        Path path2 = Path.of("homePath","test.txt");
        System.out.println(path.normalize());
        System.out.println(path.equals(path2));
        path = path.normalize();
        System.out.println(path.equals(path2));
        try {
            System.out.println(path.toRealPath());
        } catch (IOException e) {
            System.out.println("Path does not exist");
        }
        //The toAbsolutePath() method returns the absolute path of the file or directory
        System.out.println(path.toAbsolutePath());
    }

    private void resolvingRelativizePath() throws IOException {
        //Is how we concatenate strings let you pass a path or String
        Path path = Path.of("path.txt");

        Path pathResolve = testPath.resolve(path);
        if(!Files.exists(pathResolve))
            Files.createDirectories(pathResolve);

        //If an absolute paths is provided as input, that value is returned
        Path pathResolve2 = path.resolve(testPath.toAbsolutePath());

        //relativize() method for constructing the relative path from one path to another
        //The idea is to find the steps would you need to take to reach to a path from another
        //Requires both paths to be absolute or relative and on windows boths must have same root
        Path path2 = testPath.resolve("other.txt");

        System.out.println(path.relativize(path2));

    }

    private void operatingPath() throws IOException {
        //Path instances are immutable
        if(!Files.exists(testPath))
             Files.createDirectory(testPath);

        Path path = Path.of(testPath.toString(),"path.txt");
        Files.createFile(path);

        System.out.println(path.getFileName());
        System.out.println(path.getParent());
        System.out.println(path.toAbsolutePath());
        System.out.println(path.toAbsolutePath().getNameCount());
        System.out.println(path.toAbsolutePath().getName(2));
        System.out.println(path.toAbsolutePath().subpath(0,3));
        System.out.println(path.toAbsolutePath().getRoot());


        Files.list(testPath).forEach(System.out::println);
        System.out.println(Files.size(path));
        System.out.println(Files.isRegularFile(path));
        System.out.println(Files.getLastModifiedTime(path));
        System.out.println(Files.isDirectory(path));
        System.out.println(Files.exists(path));

        Files.move(path,path);
        //Files.deleteIfExists(path);

    }

    private void operatingFile() throws IOException {

        File zooFile = new File(testFile,"homeTest.txt");
        File zooFile2 =  new File(testFile,"homeTest2.txt");;

        zooFile.createNewFile();

        System.out.println(zooFile.getName());
        System.out.println(zooFile.getParent());
        System.out.println(zooFile.getAbsolutePath());
        if(zooFile.isFile()){
            System.out.println("It is a File");
            zooFile.mkdir();
            zooFile.mkdirs();

            zooFile.renameTo(zooFile2);
        }
        if(zooFile.isDirectory()){
            System.out.println("It is Directory");
            System.out.println(zooFile.listFiles());
        }

        System.out.println(zooFile2.lastModified());
        System.out.println(zooFile2.length());

        if(zooFile2.exists())
            zooFile2.delete();
    }

    private void createFilePath() {
        //Constructor of File
        File zooFile1 = new File("home.txt");
        File zooFile2 = new File("C:\\Users\\camec\\IdeaProjects\\OCPJava17\\","home.txt");
        File zooFile3 = new File(zooFile2,"new.txt");

        //Path
        Path zooPath1 = Path.of("test.txt");
        Path zooPath2 = Path.of("C:\\Users\\camec\\IdeaProjects\\OCPJava17\\","home.txt");

        System.out.println(zooFile2.exists());
        System.out.println(Files.exists(zooPath1));

        Path nowPath = zooFile1.toPath();
        File nowFIle = zooPath1.toFile();

        //FileSystems class creates instances of the abstract FileSystem class
        Path zooPath = FileSystems.getDefault().getPath("home.txt");
    }
}
