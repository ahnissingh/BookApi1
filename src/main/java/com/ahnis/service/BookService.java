package com.ahnis.service;

import com.ahnis.entity.Book;
import com.ahnis.exceptions.ResourceNotFoundException;
import com.ahnis.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = Objects.requireNonNull(bookRepository, "Book repository cannot be null");
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Iterable<Book> postBooks(Iterable<Book> bookList) {
        return bookRepository.saveAll(bookList);
    }

    public Book saveBook(Book book) {
        Objects.requireNonNull(book, "Book cannot be null");
        if (book.getId() < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        return bookRepository.save(book);
    }

    public Book getBook(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public Book deleteBook(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.delete(optionalBook.get());
            return optionalBook.get();
        } else {
            throw new ResourceNotFoundException("Not found");
        }
    }

    public Book saveOrUpdateBook(Book book) {
        Objects.requireNonNull(book, "Book cannot be null");
        if (book.getId() < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            return bookRepository.save(existingBook);
        } else {
            return bookRepository.save(book);
        }
    }
}