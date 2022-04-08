package com.example.demo.infra.repository.jpa;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
@Primary
public class SpringDataUserRepository implements UserRepository {

    private final JPAUserRepo repo;
    private final UserMapper mapper;

    SpringDataUserRepository(JPAUserRepo repo, UserMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public void save(User user) {
        repo.save(mapper.toEntity(user));
    }

    @Override
    public int count() {
        return (int) repo.count();
    }

    @Override
    public List<User> findAll() {
        return repo.findAll().stream()
                .map(mapper::toModel)
                .collect(toList());
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }
}
