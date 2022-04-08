package com.example.demo.infra.repository.jpa;

import com.example.demo.domain.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {
    UserEntity toEntity(User user) {
        return new UserEntity()
                .setId(user.getId())
                .setName(user.getName());
    }
    
    User toModel(UserEntity entity)  {
        return new User()
                .setId(entity.getId())
                .setName(entity.getName());
    }
}