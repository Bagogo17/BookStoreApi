package com.bookstore.repo.book;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {

    @Id
    private String isbn;
    private String title;
    private int year;
    private double price;
    private String genre;
    private String authors; //delimiter by ,

}
