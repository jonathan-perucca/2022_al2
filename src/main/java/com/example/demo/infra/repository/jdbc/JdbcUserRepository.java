package com.example.demo.infra.repository.jdbc;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update("insert into users (id, name) values (?, ?)", user.getId(), user.getName());
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select id, name from users", new UserRowMapper());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.execute("delete from users");
    }

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User()
                    .setId(rs.getString("id"))
                    .setName(rs.getString("name"));
        }
    }
}
