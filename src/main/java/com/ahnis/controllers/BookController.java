package com.ahnis.controllers;

import com.ahnis.entity.Book;
import com.ahnis.service.BookService;
import com.ahnis.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/books")
    public ResponseEntity<?> getBooks() {
        try{
            return ResponseEntity.ok(bookService.getBooks());
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable("id") int id) {
        try{
            return  ResponseEntity.ok(bookService.getBook(id));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
        }

    }

    @PostMapping("/books")
    public ResponseEntity<?> saveBooks(@RequestBody List<Book> booksList) {
    try{
        return ResponseEntity.ok(bookService.postBooks(booksList));
    }
    catch (Exception e){
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving List of books");
    }
    }

    @PostMapping("/book")
    public ResponseEntity<?> saveBook(@RequestBody Book book) {
        try{
            return ResponseEntity.ok(bookService.saveBook(book));

        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cant save book check json");
        }
    }

    @PatchMapping("/books")
    public ResponseEntity<?> patchBook(@RequestBody Book updatedBook) {
        try {
            // ...

            // Return a response with the updated book and a status code
            return ResponseEntity.ok(bookService.saveOrUpdateBook(updatedBook));
        } catch (Exception e) {
            // Return an error response with a status code and a message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating book: " + e.getMessage());
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") int id) {
        try{
            return ResponseEntity.ok(bookService.deleteBook(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting");
        }
    }

}
