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
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBookById(int id) {
        return jdbcTemplate.query("select * from book where book_id = ?",
                new BeanPropertyRowMapper<>(Book.class), id).stream().findFirst().orElse(null);
    }

    public int addBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            "insert into book(title, author, release_data) values(?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getRelease_data());
            return ps;
        }, keyHolder);

        return (int) keyHolder.getKeys().get("book_id");
//        int affectedRows=jdbcTemplate.update("insert into book(person_id, title, author, release_data) values(?,?,?,?)",
//                book.getPerson_id(),book.getTitle(),book.getAuthor(),book.getRelease_data());
//        if(affectedRows==1){
//            return jdbcTemplate.query("select * from book where  = ?",
//                    new BeanPropertyRowMapper<>(Book.class),id).stream().findFirst().orElse(null);
//        }
//        return -1;
    }

    public Optional<Person> getHolder(int id) {
        return jdbcTemplate.query("select person.* from person join (" +
                                "select person_id from book where book_id=?) b using(person_id)",
                new BeanPropertyRowMapper<>(Person.class),id).stream().findFirst();
    }

    public void releaseBook(int id) {
        jdbcTemplate.update("update book set person_id=null where book_id=?", id);
    }
    public void setPerson(int id, Person person) {
        jdbcTemplate.update("update book set person_id=? where book_id=?", person.getPerson_id(), id);
    }
    public void updateBook(int id, Book book) {
        jdbcTemplate.update("update book set title=?,author=?,release_data=? where book_id=?",
                book.getTitle(), book.getAuthor(), book.getRelease_data(),id);
    }
    public void deleteBook(int id) {
        jdbcTemplate.update("delete from book where book_id = ?", id);
    }
}
