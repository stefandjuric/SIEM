package com.example.siem.repository;

import com.example.siem.domain.Alarm;
import com.example.siem.domain.AlarmRule;
import com.example.siem.domain.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by djuro on 5/29/2018.
 */
public interface AlarmRepository extends MongoRepository<Alarm, String> {

    @Query("{ 'active' : true }")
    List<Alarm> findAllActive();

    @Query("{ 'alarmType' : ?0  }")
    List<Alarm> findByAlarmRule(AlarmRule alarmRule);
}
