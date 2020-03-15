package com.example.bookstore.storage;

import com.example.bookstore.type.Book;

import java.util.List;

public interface BookStorage {

    Book getBook(long id);

    List<Book> getAllBooks();

    void addBook(Book book);
}
