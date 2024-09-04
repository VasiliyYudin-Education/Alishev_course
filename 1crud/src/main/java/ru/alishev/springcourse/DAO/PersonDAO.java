package ru.alishev.springcourse.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    from jdbc lesson
//    private static int PEOPLE_COUNT;
//
//    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "1234";
//    private static Connection connection;
//
//    static{
//        PEOPLE_COUNT=0;
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public List<Person> index(){
//        clean jdbc
//        List<Person> people=new ArrayList<>();
//        try (Statement statement = connection.createStatement()) {
//            String SQL="SELECT * FROM person";
//            ResultSet result = statement.executeQuery(SQL);
//            while (result.next()) {
//                people.add(new Person(){{
//                    setId(result.getInt("id"));
//                    setName(result.getString("name"));
//                    setAge(result.getInt("age"));
//                    setEmail(result.getString("email"));
//                }});
//            }
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return people;
        return jdbcTemplate.query("SELECT * FROM person", new PersonRowMapper());
    }

    public Person show(int id){
//        from jdbc lesson
//        String SQL="SELECT * FROM person WHERE id=?";
//        try(PreparedStatement preparedStatement= connection.prepareStatement(SQL)) {
//            preparedStatement.setInt(1, id);
//            ResultSet result = preparedStatement.executeQuery();
//            if(result.next()){
//                return new Person(){{
//                    setId(result.getInt("id"));
//                    setName(result.getString("name"));
//                    setAge(result.getInt("age"));
//                    setEmail(result.getString("email"));
//                }};
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        Person person=new Person();
//        person.setName("No person with id "+id);
//        return person;

        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = ?",
                new BeanPropertyRowMapper<>(Person.class), id);
    }

    public void save(Person person){
//        from jdbc lesson
//        String SQL="INSERT INTO person (id, name, age, email) VALUES (?,?,?,?)";
//        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
//            statement.setInt(++PEOPLE_COUNT, person.getId());
//            statement.setString(2, person.getName());
//            statement.setInt(3, person.getAge());
//            statement.setString(4, person.getEmail());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        jdbcTemplate.update("INSERT INTO person (name, age, email, address) VALUES (?,?,?,?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress()
                );
    }

    public void edit(int id, Person updatedPerson){
//        from jdbc lesson
//        String SQL="UPDATE person SET name=?, age=?, email=? WHERE id=?";
//        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
//            statement.setString(1, updatedPerson.getName());
//            statement.setInt(2, updatedPerson.getAge());
//            statement.setString(3, updatedPerson.getEmail());
//            statement.setInt(4, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=?, address=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(),
                id
        );
    }

    public void remove(int id) {
//        from jdbc lesson
//        String SQL="DELETE FROM person WHERE id=?";
//        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    // testing optimization of batch update

    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long before=System.currentTimeMillis();
        for(Person person : people){
            jdbcTemplate.update("INSERT INTO person (id, name, age, email) VALUES (?,?,?,?,?)",
                    person.getId(), person.getName(), person.getAge(), person.getEmail(), person.getAddress()
            );
        }
        long after=System.currentTimeMillis();
        System.out.println("Time: "+(after-before));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; ++i) {
            people.add(new Person(i,"User_"+i,30, "test_"+i+"@mail.com","some address"));
        }
        return people;
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();
        long before=System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO person (id, name, age, email) VALUES (?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, people.get(i).getId());
                        ps.setString(2, people.get(i).getName());
                        ps.setInt(3, people.get(i).getAge());
                        ps.setString(4, people.get(i).getEmail());
                        ps.setString(5, people.get(i).getAddress());
                    }
                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                }
        );
        long after=System.currentTimeMillis();
        System.out.println("Time batch: "+(after-before));
    }

    public Person show(String email){
        return jdbcTemplate.query("SELECT * FROM person WHERE email=?",
                new BeanPropertyRowMapper<>(Person.class),email).stream().findAny().orElse(null);
    }
}
