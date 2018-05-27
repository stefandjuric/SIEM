package com.example.siem.repository;

import com.example.siem.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by djuro on 3/31/2018.
 */
public interface LogRepository extends JpaRepository<Log,Long>
{
    List<Log> findByType(String type);

    @Query("SELECT w FROM Log w WHERE w.date >= :date1 AND w.date <= :date2")
    List<Log> findByDate(@Param("date1") Date date1, @Param("date2") Date date2);
}

