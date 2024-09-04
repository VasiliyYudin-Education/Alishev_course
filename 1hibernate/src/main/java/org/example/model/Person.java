package org.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "owner", /*added in lesson 54*/cascade = CascadeType.PERSIST)
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.items = new ArrayList<Item>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addItem(Item item) {
        if(items==null) {items=new ArrayList<>();}
        item.setOwner(this);
        items.add(item);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
