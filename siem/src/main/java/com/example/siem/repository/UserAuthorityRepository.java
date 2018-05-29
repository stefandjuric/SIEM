package com.example.siem.repository;

import com.example.siem.domain.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by djuro on 11/29/2017.
 */

/**
 * Spring Data JPA repository for the UserAuthority entity.
 */


public interface UserAuthorityRepository extends JpaRepository<UserAuthority,Long> {

}
