package com.example.siem.repository;

import com.example.siem.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by djuro on 3/31/2018.
 */
public interface LogRepository extends JpaRepository<Log,Long> {
}
