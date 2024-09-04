package ru.alishev.springcourse.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int person_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min=3,max=200, message = "Name length should be between 3 and 200 characters")
    @Column(name = "name")
    private String name;

    @Min(value=1931, message = "Person should be born after 1930")
    @Column(name = "birth_year")
    private int birth_year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {
    }

    public Person(int person_id, String name, int birth_year) {
        this.person_id = person_id;
        this.name = name;
        this.birth_year = birth_year;
    }

    public int getPerson_id() {
        return person_id;
    }

    public @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 200, message = "Name length should be between 3 and 200 characters")
    String getName() {
        return name;
    }

    @Min(value = 1931, message = "Person should be born after 1930")
    public int getBirth_year() {
        return birth_year;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setName(@NotEmpty(message = "Name should not be empty")
                        @Size(min = 3, max = 200, message = "Name length should be between 3 and 200 characters")
                        String name) {
        this.name = name;
    }

    public void setBirth_year(@Min(value = 1931, message = "Person should be born after 1930")
                              int birth_year) {
        this.birth_year = birth_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void updateByOtherPersonObject(Person person) {
        this.name = person.getName();
        this.birth_year = person.getBirth_year();
    }
}
