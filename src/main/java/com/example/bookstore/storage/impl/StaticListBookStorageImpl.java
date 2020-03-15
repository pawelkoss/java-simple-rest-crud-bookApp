package com.example.bookstore.storage.impl;

import com.example.bookstore.storage.BookStorage;
import com.example.bookstore.type.Book;

import java.util.ArrayList;
import java.util.List;

public class StaticListBookStorageImpl {

    private static List<Book> bookstorage = new ArrayList<Book>();

    public Book getBook(long id){
        for(Book book:bookstorage){
            if(book.getId() == id){
                return book;
            }
        }
        return null;
    }

    public  List<Book> getAllBooks() {
        return bookstorage;
    }

    public void addBook(Book book){
        bookstorage.add(book);
    }
}
