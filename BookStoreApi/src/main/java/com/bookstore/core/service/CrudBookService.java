package com.bookstore.core.service;


import com.bookstore.repo.book.Book;
import com.bookstore.repo.book.BookRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CrudBookService {

    @Autowired
    BookRepository bookRepository;

    public ResponseEntity<String> createBookRecord (Book book) {
        try {
            bookRepository.save(book);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable To Create Record due to: " + ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Record Successfully Created");
    }

    public ResponseEntity<String> updateBookRecord (Book updatedBook, String isbn) {
        try {
            Optional<Book> bookOpt = bookRepository.findById(isbn);
            if (bookOpt.isPresent()) {
                Book oldBook = bookOpt.get();
                oldBook.setTitle(updatedBook.getTitle());
                oldBook.setYear(updatedBook.getYear());
                oldBook.setPrice(updatedBook.getPrice());
                oldBook.setGenre(updatedBook.getGenre());
                oldBook.setAuthors(updatedBook.getAuthors());
                bookRepository.save(oldBook);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable To Find Record: " + isbn);
            }
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable To Update Record due to: " + ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Record Successfully Updated");
    }

    public ResponseEntity<String> findByTitleOrAuthor(String title, String author) {
        List<Book> bookList = new ArrayList<>();
        try {
            if (title != null && author != null) {
                bookList = bookRepository.findByTitle(title).stream().filter(b -> Arrays.stream(b.getAuthors().split(",")).anyMatch(a -> a.equals(author))).toList();
            } else if (author == null) {
                bookList = bookRepository.findByTitle(title);
            } else
                bookList = bookRepository.findByAuthors(author);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable To Find Record due to: " + ex.getMessage());
        }
        Gson gson = new Gson();
        String bookListJson = gson.toJson(bookList);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookListJson);
    }

    public ResponseEntity<String> deleteBookRecord(String isbn) {
        try {
            bookRepository.deleteById(isbn);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable To Delete Record due to: " + ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Record Successfully Deleted");
    }


}
