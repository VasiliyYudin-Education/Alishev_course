package ru.alishev.springcourse.DAO;

import org.springframework.jdbc.core.RowMapper;
import ru.alishev.springcourse.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet result, int rowNum) throws SQLException {
        return new Person(){{
                    setId(result.getInt("id"));
                    setName(result.getString("name"));
                    setAge(result.getInt("age"));
                    setEmail(result.getString("email"));
                    setAddress(result.getString("address"));
                }};
    }
}
