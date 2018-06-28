package com.example.siem.service.implementation;

import com.example.siem.domain.AlarmRule;
import com.example.siem.repository.AlarmRuleRepository;
import com.example.siem.service.AlarmRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by djuro on 6/1/2018.
 */
@Service
public class AlarmRuleServiceImpl implements AlarmRuleService
{
    private AlarmRuleRepository alarmRuleRepository;

    @Autowired
    public AlarmRuleServiceImpl(AlarmRuleRepository alarmRuleRepository)
    {
        this.alarmRuleRepository = alarmRuleRepository;
    }


    @Override
    public AlarmRule addAlarmRule(AlarmRule alarmRule)
    {
        AlarmRule newAlarmRule = this.alarmRuleRepository.save(alarmRule);
        return newAlarmRule;
    }

    @Override
    public List<AlarmRule> getAllAlarmRule()
    {
        List<AlarmRule> alarmRules = this.alarmRuleRepository.findAll();
        return  alarmRules;
    }

}
