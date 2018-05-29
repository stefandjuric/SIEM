package com.example.siem.repository;

import com.example.siem.domain.Admin;
import com.example.siem.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by djuro on 11/19/2017.
 */

/**
 * Spring Data JPA repository for the Admin entity.
 */


public interface AdminRepository extends JpaRepository<Admin,Integer>
{
}
