package com.example.siem.repository;

import com.example.siem.domain.OrdinaryUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by djuro on 5/25/2018.
 */
public interface OrdinaryUserRepository extends JpaRepository<OrdinaryUser,Long> {
}
