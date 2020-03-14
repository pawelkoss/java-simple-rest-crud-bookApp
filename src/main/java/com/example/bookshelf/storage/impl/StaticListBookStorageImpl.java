package com.example.bookshelf.storage.impl;

import com.example.bookshelf.storage.BookStorage;
import com.example.bookshelf.type.Book;

import java.util.ArrayList;
import java.util.List;

public class StaticListBookStorageImpl implements BookStorage {

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
