package com.bookstore.repo.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByTitle(String title);
    List<Book> findByAuthors(String Author);
}