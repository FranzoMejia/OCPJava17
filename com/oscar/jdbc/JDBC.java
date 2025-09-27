package com.oscar.jdbc;

import com.oscar.interfaces.Chapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC implements Chapter {
    @Override
    public void start() {
        System.out.println("JDBC chapter started");
        //Database is a organized collection of data
        //Relational database is a database that stores data in tables with rows and columns
        //SQL is a language used to interact with relational databases
        //There are two main ways to access a database in Java: JDBC and JPA
        //JDBC is a low-level API that allows you to execute SQL statements directly
        //JPA is a high-level API that allows you to interact with a database using objects

        //Non-relational databases are databases that do not use tables to store data
        //Examples of non-relational databases are MongoDB, Cassandra, and Redis

        //JDBC is a part of Java Standard Edition
        //JDBC is a set of classes and interfaces that allow you to connect to a database, execute SQL statements, and retrieve results
        //JDBC Interfaces: Connection, Statement, PreparedStatement, ResultSet, DriverManager

        //JDBC Drivers: Establish a connection to a specific database
        //JDBC Connection: Send commands and queries to the database
        //JDBC PreparedStatement: Execute parameterized SQL statements
        //JDBC CallableStatement: Execute stored procedures
        //JDBC ResultSet: Retrieve and manipulate data returned from a query

        //Interfaces are declared in the JDK, no need to import external libraries
        //The concrete classes come from external libraries (JDBC Drivers)

        String url = "jdbc:sqlserver://localhost;encrypt=true;integratedSecurity=true;";
        //Server=localhost\SQLEXPRESS;Database=master;Trusted_Connection=True;
        try (Connection conn = DriverManager.getConnection(url);
             ) {
            if (conn != null) {
                 try (PreparedStatement pstmt = conn.prepareStatement("SELECT TOP 10 * FROM sys.objects")) {
                     boolean hasResultSet = pstmt.execute();
                     if (hasResultSet) {
                         System.out.println("Connection established successfully.");
                         var rs = pstmt.getResultSet();
                         while (rs.next()) {
                             System.out.println("Object Name: " + rs.getString("name"));
                         }
                     } else {
                         System.out.println("No results returned.");
                     }
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
