package com.example.demo.infra.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAUserRepo extends JpaRepository<UserEntity, String> {
}
