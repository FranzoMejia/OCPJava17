package com.oscar.io;

import com.oscar.interfaces.Chapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

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


            Files.createDirectory(testPath);
            Files.createDirectory(testFile.toPath());

            createFilePath();
            operatingFile();
            operatingPath();
            resolvingRelativizePath();
            normalizingPath();
            directories();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteOnStartup(Path testPath) {
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
        Path sourcePath = Path.of("homePath","testDir","subDir","source.txt");
        Files.createDirectories(sourcePath);
        Path targetPath = Path.of("homePath","testDir","subDir","target.txt");

        Files.copy(sourcePath, targetPath);


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
