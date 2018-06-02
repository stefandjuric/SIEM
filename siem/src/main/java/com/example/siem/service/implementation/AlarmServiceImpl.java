package com.example.siem.service.implementation;

import com.example.siem.domain.Alarm;
import com.example.siem.repository.AlarmRepository;
import com.example.siem.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by djuro on 6/1/2018.
 */
@Service
public class AlarmServiceImpl implements AlarmService
{
    public AlarmRepository alarmRepository;

    @Autowired
    public AlarmServiceImpl(AlarmRepository alarmRepository)
    {
        this.alarmRepository = alarmRepository;
    }

    public List<Alarm> getAllAlarms()
    {
        List<Alarm> alarms = this.alarmRepository.findAllActive();
        return alarms;
    }
}
