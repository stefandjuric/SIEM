package com.example.siem.repository;

import com.example.siem.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by djuro on 3/31/2018.
 */
public interface LogRepository extends MongoRepository<Log,String>
{
    @Query("{ 'type' : ?0 }")
    List<Log> findByType(String type);

    @Query("{ 'date' : { $gt: ?0, $lt: ?1 } }")
    List<Log> findByDate(Date date1, Date date2);


}

