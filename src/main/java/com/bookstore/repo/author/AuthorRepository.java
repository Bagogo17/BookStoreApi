package com.bookstore.repo.author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,String> {

    Author findByName(String name);
}