package com.example.bookstore.storage;

import com.example.bookstore.type.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BookDAOImpl implements BookStorage{

    private final static String DBURL = "jdbc:postgresql://localhost:5432/test";
    private final static String DBUSER = "postgres";
    private final static String DBPASS = "785436";
    private final static String DBDRIVER = "org.postgresql.Driver";


    private Connection connection;
    private Statement statement;
    private String query;


    //parser zapytań SQL dla obiektów klasy Article
    //private SQLArticleParser sqlArticleParser;

    public BookDAOImpl() {
        //inicjalizacja parserów
        //sqlArticleParser = new SQLArticleParser();
    }

    public void addBook(Book book) {
        //query = sqlArticleParser.createSaveQuery(book);
        query = "INSERT INTO books (title, body) VALUES ('" + book.getTitle() + "', '" + book.getAuthor() +"');";

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

    @Override
    public Book getBook(long id) {
        query = "Select * FROM books WHERE bookid='"+id+"'";
        Book book = new Book();
        try {
            Class.forName(DBDRIVER).newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            rs.next();
            book = bookFromDB(rs);

            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return book;
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        query = "Select * FROM books";

        try {
            Class.forName(DBDRIVER).newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                Book book = bookFromDB(rs);
                bookList.add(book);
            }


            //zwolnienie zasobów i zamknięcie połączenia
            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }

        return bookList;

    }

    private static Book bookFromDB(ResultSet rs){
        Book book = new Book();
        try{
            book.setId(rs.getLong(7));
            book.setTitle(rs.getString(2));
            book.setAuthor(rs.getString(3));
            book.setPageSum(rs.getInt(4));
            book.setYearOfPublished(rs.getInt(5));
            book.setPublishingHouse(rs.getString(6));

        }catch(SQLException e) {
            e.printStackTrace();
        }

        return book;
    }
}
