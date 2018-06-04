package com.example.siem.service.implementation;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.siem.domain.Alarm;
import com.example.siem.domain.AlarmRule;
import com.example.siem.domain.Log;
import com.example.siem.repository.AlarmRepository;
import com.example.siem.repository.AlarmRuleRepository;
import com.example.siem.repository.LogRepository;
import com.example.siem.service.SiemAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by djuro on 3/31/2018.
 */
@Service
public class SiemAgentServiceImpl implements SiemAgentService
{

    private final static  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    private LogRepository logRepository;

    private MongoTemplate mongoTemplate;

    private AlarmRuleRepository alarmRuleRepository;

    private AlarmRepository alarmRepository;

    @Autowired
    public SiemAgentServiceImpl(LogRepository logRepository,
                                MongoTemplate mongoTemplate,
                                AlarmRuleRepository alarmRuleRepository,
                                AlarmRepository alarmRepository)
    {
        this.logRepository = logRepository;
        this.mongoTemplate = mongoTemplate;
        this.alarmRuleRepository = alarmRuleRepository;
        this.alarmRepository = alarmRepository;

    }

    @Override
    public Log saveLog(String log) throws ParseException {

        String lines[] = log.split("&");
        System.out.println(log);
        for (int i = 0; i < lines.length; i++){
            lines[i] = lines[i].replace('+', ' ');
            lines[i] = lines[i].replace("%3A", ":");
            lines[i] = lines[i].replace("%5B", "{");
            lines[i] = lines[i].replace("%5D", "}");
            String[] a =lines[i].split("=");
            if (a.length >= 2){
            lines[i] = a[1];
            }
        }
        Log saved = new Log(lines[0], lines[1], lines[2], lines[3], lines[4], lines[5], lines[6]);
        saved = this.logRepository.save(saved);
        return saved;
    }

    @Override
    public List<Log> getLogs()
    {
        List<Log> logs = this.logRepository.findAll();
        return logs;
    }

    @Override
    public List<Log> searchByType(String type)
    {
        System.out.println("Type:      "+type);
        List<Log> logs = this.logRepository.findByType(type);
        List<Log> logs1 = new ArrayList<>();
        for(Log l : logs)
        {
            if(l.getType().equals(type)) {
                logs1.add(l);
            }
        }

        return logs1;
    }

    @Override
    public List<Log> searchByDate(Date from, Date to)
    {
        List<Log> logs = this.logRepository.findByDate(from, to);
        return logs;
    }

    @Override
    public List<Log> searchByRegex(String regex)
    {
        List<Log> logs = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        String[] parts = regex.split(" and ");
        Query query = new Query();
        Boolean criteria=false;
        for(String p : parts)
        {
            System.out.println("11111111111");
            System.out.println(p);
            if(p.contains("=="))
            {
                String[] attributes = p.split("==");
                System.out.println(attributes[0]);
                System.out.println(attributes[1]);
                if(attributes[0].equals("type")) { query.addCriteria(Criteria.where("type").is(attributes[1])); criteria=true; System.out.println(attributes[1]);}
                else if(attributes[0].equals("description")) { query.addCriteria(Criteria.where("description").is(attributes[1])); criteria=true; System.out.println(attributes[1]);}
                else return null;

            }
            if(p.contains(">"))
            {
                String[] attributes = p.split(">");
                if(attributes[0].equals("date"))
                {
                    try {

                        query.addCriteria(Criteria.where("date").gt(formatter.parse(attributes[1])));
                        criteria=true;
                        //date1 = formatter.parse(attributes[1]);
                    } catch (ParseException e) {
                        return null;
                    }
                }
                else return null;
            }
            if(p.contains("<"))
            {
                String[] attributes = p.split(">");
                if(attributes[0].equals("date"))
                {
                    try {
                        query.addCriteria(Criteria.where("date").lt(formatter.parse(attributes[1])));
                        criteria=true;
                        //date2 = formatter.parse(attributes[1]);
                    } catch (ParseException e) {
                        return null;
                    }
                }
                else return null;
            }
        }
        if(criteria) logs = mongoTemplate.find(query, Log.class);
        return logs;

    }

    public void addAlarm(Log log)
    {
        List<AlarmRule> alarmRules = this.alarmRuleRepository.findAll();
        for(AlarmRule ar : alarmRules)
        {
            if(ar.getTypeFlag() &&  !ar.getIpAddressFlag() && !ar.getIpAddressFlag())
            {
                if(ar.getType().equals(log.getType()))
                {
                    System.out.println("usao u addAlarm");
                    Alarm alarm = new Alarm(ar, log.getType(), log.getDescription(), "172.16.254.1", ar.getRepetition(), true);
                    alarm.addDate(new Date());
                    this.alarmRepository.save(alarm);
                }
            }
        }
    }
}
