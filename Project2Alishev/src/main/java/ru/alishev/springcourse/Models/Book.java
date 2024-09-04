package ru.alishev.springcourse.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int book_id;

    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "person_id")
    private Person owner;

    @NotEmpty(message = "Title should not be empty")
    @Size(min=2,max=200, message = "Title should contain from 2 to 200 characters")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author name should not be empty")
    @Size(min=2,max=200, message = "Author name should contain from 2 to 100 characters")
    @Column(name = "author")
    private String author;

    @Column(name = "release_data")
    private int release_data;

    public Book() {
    }

    public Book(String author, String title, int release_data, int book_id) {
        this.author = author;
        this.title = title;
        this.release_data = release_data;
        this.book_id = book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setTitle(@NotEmpty(message = "Title should not be empty") @Size(min = 2, max = 200, message = "Title should contain from 2 to 200 characters") String title) {
        this.title = title;
    }

    public void setAuthor(@NotEmpty(message = "Author name should not be empty") @Size(min = 2, max = 200, message = "Author name should contain from 2 to 100 characters") String author) {
        this.author = author;
    }

    public void setRelease_data(int release_data) {
        this.release_data = release_data;
    }

    public int getBook_id() {
        return book_id;
    }

    public @NotEmpty(message = "Title should not be empty") @Size(min = 2, max = 200, message = "Title should contain from 2 to 200 characters") String getTitle() {
        return title;
    }

    public @NotEmpty(message = "Author name should not be empty") @Size(min = 2, max = 200, message = "Author name should contain from 2 to 100 characters") String getAuthor() {
        return author;
    }

    public int getRelease_data() {
        return release_data;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public void updateByOtherBookObj(Book book){
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.release_data = book.getRelease_data();
    }
}
