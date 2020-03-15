package com.example.bookstore.storage;

import com.example.bookstore.type.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ArticleDAOImpl implements BookStorage{

    private final static String DBURL = "jdbc:mysql://localhost/db_art?serverTimezone=UTC&useSSL=False";
    private final static String DBUSER = "root";
    private final static String DBPASS = "123456";
    private final static String DBDRIVER = "com.mysql.cj.jdbc.Driver";

    //obiekt tworzący połączenie z bazą danych.
    private Connection connection;
    //obiekt pozwalający tworzyć nowe wyrażenia SQL
    private Statement statement;
    //zapytanie SQL
    private String query;
    //parser zapytań SQL dla obiektów klasy Article
    //private SQLArticleParser sqlArticleParser;

    public ArticleDAOImpl() {
        //inicjalizacja parserów
        //sqlArticleParser = new SQLArticleParser();
    }

    public void save(Book book) {
        //query = sqlArticleParser.createSaveQuery(article);
        query = "INSERT INTO article (title, body) VALUES ('" + book.getTitle() + "', '" + book.getAuthor() +"');";

        try {
            Class.forName(DBDRIVER).newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            statement.executeUpdate(query);


            //zwolnienie zasobów i zamknięcie połączenia
            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException
               | ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }


    }
    public void getAllBooks() {
        List<Book> books = new ArrayList<>();
        query = "Select * FROM article";

        try {
            Class.forName(DBDRIVER).newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                booksFromDB(rs);
            }


            //zwolnienie zasobów i zamknięcie połączenia
            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }


    }
    private static String dataFromDB;
    private static void booksFromDB(ResultSet rs){
        try{
            dataFromDB = rs.getString(1);
            System.out.println("\n" + dataFromDB + " ");
            dataFromDB = rs.getString(2);
            System.out.println(dataFromDB + " ");
            dataFromDB = rs.getString(3);
            System.out.println(dataFromDB);
            dataFromDB = rs.getString(4);
            System.out.println(dataFromDB);

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
