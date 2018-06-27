package com.example.siem.controller;

import com.example.siem.domain.Alarm;
import com.example.siem.domain.AlarmRule;
import com.example.siem.domain.Log;
import com.example.siem.service.AlarmRuleService;
import com.example.siem.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by djuro on 6/1/2018.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/alarm")
public class AlarmController
{
    private AlarmService alarmService;

    private AlarmRuleService alarmRuleService;

    @Autowired
    public AlarmController(AlarmService alarmService,
                           AlarmRuleService alarmRuleService)
    {
        this.alarmService = alarmService;
        this.alarmRuleService = alarmRuleService;
    }

    @RequestMapping(value = "/addAlarmRule", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlarmRule> addAlarmRule(@RequestBody AlarmRule alarmRule) throws ParseException {

        AlarmRule alarmRule1 = this.alarmRuleService.addAlarmRule(alarmRule);
        if(alarmRule1 == null)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(alarmRule1, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/getAlarms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Alarm>> getAlarms()
    {
        List<Alarm> alarms = this.alarmService.getAllAlarms();
        return new ResponseEntity<>(alarms, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllAlarmRules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlarmRule>> getAllAlarmRules()
    {
        List<AlarmRule> alarmRules = this.alarmRuleService.getAllAlarmRule();
        return new ResponseEntity<>(alarmRules, HttpStatus.OK);
    }


}
