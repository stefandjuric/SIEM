package com.example.siem.repository;

import com.example.siem.domain.AlarmRule;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by djuro on 5/29/2018.
 */
public interface AlarmRuleRepository extends MongoRepository<AlarmRule, String> {
}
