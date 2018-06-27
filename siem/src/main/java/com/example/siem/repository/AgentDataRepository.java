package com.example.siem.repository;

import com.example.siem.domain.AgentData;
import com.example.siem.domain.AlarmRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AgentDataRepository extends MongoRepository<AgentData, String> {
}
