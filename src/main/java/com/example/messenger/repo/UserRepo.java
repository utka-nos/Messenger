package com.example.messenger.repo;

import com.example.messenger.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo extends MongoRepository<User, String> {
    User findByLogin(String username);
}
