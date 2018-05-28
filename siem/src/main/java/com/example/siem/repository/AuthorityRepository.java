package com.example.siem.repository;

import com.example.siem.domain.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by djuro on 11/19/2017.
 */

/**
 * Spring Data JPA repository for the Authority entity.
 */


public interface AuthorityRepository extends MongoRepository<Authority,Long>
{
    Authority findByName(String name);
}
