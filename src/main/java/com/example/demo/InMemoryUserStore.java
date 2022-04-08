package com.example.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
class InMemoryUserStore implements UserRepository {
    private final Map<String, User> db;

    InMemoryUserStore(Map<String, User> db) {
        this.db = db;
    }

    public void save(User user) {
        db.put(user.getId(), user);
    }

    public int count() {
        return db.size();
    }

    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }
}

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

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User()
                    .setId(rs.getString("id"))
                    .setName(rs.getString("name"));
        }
    }
}

@Repository
interface JPAUserRepo extends JpaRepository<User, String> {
}

@Repository
@Primary
class SpringDataUserRepository implements UserRepository {

    private final JPAUserRepo repo;

    SpringDataUserRepository(JPAUserRepo repo) {
        this.repo = repo;
    }

    @Override
    public void save(User user) {
        repo.save(user);
    }

    @Override
    public int count() {
        return (int) repo.count();
    }

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }
}

interface UserRepository {
    void save(User user);
    int count();
    List<User> findAll();
}