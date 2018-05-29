package com.example.siem.repository;

import com.example.siem.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by djuro on 11/19/2017.
 */

/**
 * Spring Data JPA repository for the Authority entity.
 */


public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    @Query("SELECT w FROM Authority w WHERE w.name = :name")
    Authority findByName(@Param("name") String name);
}
