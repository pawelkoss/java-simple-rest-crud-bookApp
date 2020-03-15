package com.example.bookstore.controller;

import com.example.bookstore.storage.BookDAOImpl;
import com.example.bookstore.storage.BookStorage;
import com.example.bookstore.storage.impl.StaticListBookStorageImpl;

import com.example.bookstore.type.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;

import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.Response.Status.BAD_REQUEST;
import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.NOT_FOUND;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class BookController {

    private final static String BOOK_ID_PARAM_NAME = "bookId";

    public BookStorage bookStorage = new BookDAOImpl();
    public Response serveGetBookRequest(NanoHTTPD.IHTTPSession session){


        Map<String, List<String>> requestParameters = session.getParameters();

        if(requestParameters.containsKey(BOOK_ID_PARAM_NAME)) {
            List<String> bookIdParams = requestParameters.get(BOOK_ID_PARAM_NAME);
            String bookIdParam = bookIdParams.get(0);
            long bookId = 0;

            try{
                bookId = Long.parseLong(bookIdParam);
            }catch (NumberFormatException nfe){
                System.err.println("Error during convert request param: \n" + nfe);
                return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Request param bookId have to be a number");
            }
            Book book = bookStorage.getBook(bookId);

            if (book !=null) {
                try{
                    ObjectMapper objectMapper = new ObjectMapper();
                    String response = objectMapper.writeValueAsString(book);
                    return newFixedLengthResponse(OK, "application/json", response);
                }catch (JsonProcessingException e){
                    System.err.println("Error during process request \n" + e);
                    return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error cant read all book");
                }
            }
            return newFixedLengthResponse(NOT_FOUND, "application/json", "");
        }

        return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Uncorrected request params");
    }



    public Response serveGetBooksRequest(NanoHTTPD.IHTTPSession session) {

        ObjectMapper objectMapper = new ObjectMapper();
        String response = "";

        try{
            response = objectMapper.writeValueAsString(bookStorage.getAllBooks());
        }catch (JsonProcessingException e){
            System.err.println("Error during process request: \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error cant read all book");
        }
        return newFixedLengthResponse(OK, "application/json", response);

    }



    public Response serveAddBookRequest(NanoHTTPD.IHTTPSession session) {

        ObjectMapper objectMapper = new ObjectMapper();
        long randomBookId = System.currentTimeMillis();

        String lengthHeader = session.getHeaders().get("content-length");
        int contentLength = Integer.parseInt(lengthHeader);
        byte[] buffer = new byte[contentLength];

        try{
            session.getInputStream().read(buffer, 0, contentLength);
            String requestBody = new String(buffer).trim();
            Book requestBook = objectMapper.readValue(requestBody, Book.class);
            requestBook.setId(randomBookId);

            bookStorage.addBook(requestBook);

        } catch (Exception e){
            System.err.println("Error during process request: \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error book hasnt been added");
        }

        return newFixedLengthResponse(OK, "text/plain", "Book has been successfully added, id=" + randomBookId);
    }




}
