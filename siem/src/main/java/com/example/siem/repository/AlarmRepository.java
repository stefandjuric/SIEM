package com.example.siem.repository;

import com.example.siem.domain.Alarm;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by djuro on 5/29/2018.
 */
public interface AlarmRepository extends MongoRepository<Alarm, String> {
}
