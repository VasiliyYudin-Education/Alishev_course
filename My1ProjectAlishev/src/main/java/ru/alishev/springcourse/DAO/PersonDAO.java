package ru.alishev.springcourse.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.Models.Book;
import ru.alishev.springcourse.Models.Person;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPersons() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper(Person.class));
    }

    public Person getPersonById(int id) {
        return jdbcTemplate.query("select * from person where person_id=?",new BeanPropertyRowMapper<>(Person.class),id)
                .stream().findFirst().orElse(null);
    }

    public int addPerson(Person person) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            "insert into person(name,birth_year) values(?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,person.getName());
            ps.setInt(2, person.getBirth_year());
            return ps;
        }, keyHolder);

        System.out.println(keyHolder.getKeys());

        return (int) keyHolder.getKeys().get("person_id");
//        int affectedRows=jdbcTemplate.update("insert into person(name,birth_year) values(?,?)",
//                new BeanPropertyRowMapper<>(Person.class),
//                person.getName(),person.getBirth_year());
//        if(affectedRows==1){
//            return jdbcTemplate.query("select * from person where name=?",
//                            new BeanPropertyRowMapper<>(Person.class),person.getName())
//                    .stream().findFirst().get().getPerson_id();
//        }
//        return -1;
    }

    public void updatePerson(int id,Person person) {
        jdbcTemplate.update("update person set name=?, birth_year=? where person_id=?",
                person.getName(),person.getBirth_year(),id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("delete from person where person_id=?",id);
    }

    public List<Book> getAllBooks(int person_id) {
        return jdbcTemplate.query("select * from book where person_id=?",
                new BeanPropertyRowMapper<>(Book.class),person_id);

    }

    public Optional<Person> getPersonByName(String name) {
        return jdbcTemplate.query("select * from person where name=?",
                new BeanPropertyRowMapper<>(Person.class),name).stream().findFirst();
    }
}
