package ru.alishev.springcourse.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min=2,max=30,message = "Name length should be between 2 and 30")
    @Column(name="name")
    private String name;

    @Min(value=1,message = "Age should be>0")
    @Column(name="age")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name="email")
    private String email;

    //Country, City, Postal code(6 numbers)
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in format:\n" +
            "Country, City, Postal code(6 numbers)")
    @Column(name="address")
    private String address;

    public void updateByOtherPerson(Person person) {
        this.name = person.getName();
        this.age = person.getAge();
        this.email = person.getEmail();
        this.address = person.getAddress();
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Person() {
    }

    public Person(int id, String name, int age, String email, String address) {
        this.id = id;
        this.name = name;
        this.age=age;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
