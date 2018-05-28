package com.example.siem.repository;

import com.example.siem.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by EmulatE on 02-Nov-17.
 */

/**
 * Spring Data JPA repository for the User entity.
 */


public interface UserRepository extends MongoRepository<User,Integer> {

    User findByUsername(String username);

    User findById(String userId);
}
