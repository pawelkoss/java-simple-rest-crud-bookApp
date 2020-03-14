package com.example.bookshelf.controller;

import com.example.bookshelf.storage.BookStorage;
import com.example.bookshelf.storage.impl.StaticListBookStorageImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;

public class BookController {

    private BookStorage bookStorage = new StaticListBookStorageImpl();
    public Response serveGetBookRequest(NanoHTTPD.IHTTPSession session){
        return null;
    }
    public Response serveGetBooksRequest(NanoHTTPD.IHTTPSession session) {
        return null;
    }
    public Response serveAddBookRequest(NanoHTTPD.IHTTPSession session) {
        return null;
    }

    ObjectMapper objectMapper = new

}
