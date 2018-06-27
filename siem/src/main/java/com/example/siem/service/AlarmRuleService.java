package com.example.siem.service;

import com.example.siem.domain.AlarmRule;

import java.util.List;

/**
 * Created by djuro on 6/1/2018.
 */
public interface AlarmRuleService {

    AlarmRule addAlarmRule(AlarmRule alarmRule);

    List<AlarmRule> getAllAlarmRule();
}
