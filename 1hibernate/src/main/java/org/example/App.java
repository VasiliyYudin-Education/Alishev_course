package org.example;


import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration config = new Configuration().addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);
        try (SessionFactory factory = config.buildSessionFactory()) {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
//            get object
//1            Person person=session.get(Person.class, 1);

//            save object
//2            Person person = new Person("Vasya",20);
//2            session.persist(person);

//            update object using commit
//3            Person person = session.get(Person.class, 2);
//3            person.setAge(23);

//            remove object
//4            Person person = session.get(Person.class, 2);
//4            session.remove(person);

//5            test id after commit
//5            Person person = new Person("Korya",21);
//5            session.persist(person);

//            hql select
//            List<Person> list = session.createQuery("FROM Person where age>20",Person.class)
//                    .getResultList();
//            System.out.println(list);


//            hql delete
//            session.createMutationQuery("delete from Person where age<21").executeUpdate();

//            one to many get list
//            Person person = session.get(Person.class, 3);
//            System.out.println(person);
//            List<Item> items=person.getItems();
//            System.out.println("Items: "+items);

//            one to many get owner
//            Item item=session.get(Item.class, 5);
//            System.out.println(item);
//            System.out.println(item.getOwner());

//            one to many add new item to list (don't forget to refresh owner's info)
//            Person person = session.get(Person.class, 2);
//            Item item = new Item("Item test", person);
//            session.persist(item);
//            session.refresh(person);
////            or
//            person.getItems().add(item);

//            one to many save without cascade
//            Person person = new Person("Karishka", 22);
//            Item item = new Item("Item for Karishka", person);
//            person.getItems().add(item);
//            session.persist(person);
//            session.persist(item);

            //one to many clear list
//            Person person = new Person("Karishka", 22);
//            List<Item> items = person.getItems();
//            for (Item item : items) {
//                session.remove(item);
//            }
//            person.getItems().clear();

//            lesson 54, test cascade
//            Person person = new Person("Karishka test cascade", 22);
//            Item item = new Item("Item for Karishka", person);
//            person.getItems().add(item);
//            session.persist(person);

//            n+1 problem
//            List<Person> people = session.createQuery("from Person", Person.class).list();
//            for (Person p : people) {
//                System.out.println(p+", Items: "+p.getItems());
//                System.out.println("=============");
//            }
//            solution
            List<Person> people = session
                    .createQuery("select p from Person p left join fetch p.items", Person.class).list();
            for (Person p : people) {
                System.out.println(p+", Items: "+p.getItems());
                System.out.println("=============");
            }
            session.getTransaction().commit();
//1,2,3,4,5            System.out.println(person);
        }
    }
}
