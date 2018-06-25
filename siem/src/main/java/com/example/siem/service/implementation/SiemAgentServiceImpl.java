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
        Log saved = new Log(lines[0],lines[1],lines[2],lines[3],lines[4],lines[5],lines[6]);
        saved = this.logRepository.save(saved);
        this.addAlarm(saved);
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
        regex = regex.replace("\"","");
        List<Log> logs = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        String[] parts = regex.split(" and ");
        Query query = new Query();
        Boolean criteria=false;
        for(String p : parts)
        {
            p = p.trim();
            System.out.println("11111111111");
            System.out.println(p);
            if(p.contains("=="))
            {
                String[] attributes = p.split("==");
                System.out.println(attributes[0]);
                System.out.println(attributes[1]);
                attributes[0] = attributes[0].trim();
                attributes[1] = attributes[1].trim();
                System.out.println(attributes[0]);
                System.out.println(attributes[1]);
                if(attributes[0].equals("type")) { query.addCriteria(Criteria.where("type").is(attributes[1])); criteria=true; System.out.println(attributes[1]);}
                else if(attributes[0].equals("description")) { query.addCriteria(Criteria.where("description").is(attributes[1])); criteria=true; System.out.println(attributes[1]);}
                else if(attributes[0].equals("host")) { query.addCriteria(Criteria.where("host").is(attributes[1])); criteria=true; System.out.println(attributes[1]);}
                else if(attributes[0].equals("facility")) { query.addCriteria(Criteria.where("facility").is(attributes[1])); criteria=true; System.out.println(attributes[1]);}
                else if(attributes[0].equals("username")) { query.addCriteria(Criteria.where("tag").is(attributes[1])); criteria=true; System.out.println(attributes[1]);}
                else if(attributes[0].equals("ip")) { query.addCriteria(Criteria.where("ip").is(attributes[1])); criteria=true; System.out.println(attributes[1]);}
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
           Boolean flag = true;

            if(!ar.getType().equals(log.getType()) && ar.getTypeFlag()) flag = false;

            if(!ar.getDescription().equals(log.getDescription()) && ar.getDescriptionFlag()) flag = false;

            if(!ar.getIpAddress().equals(log.getIp()) && ar.getIpAddressFlag()) flag = false;

            if(!ar.getUsername().equals(log.getTag()) && ar.getUsernameFlag()) flag = false;

            if(!ar.getDate().equals(log.getDate()) && ar.getDateFlag()) flag = false;

            if(!ar.getHost().equals(log.getHost()) && ar.getHostFlag()) flag = false;

            if(!ar.getFacility().equals(log.getFacility()) && ar.getFacilityFlag()) flag = false;

            if(flag) this.checkRepetition(log, ar);
        }
    }


    public List<Alarm> checkIfActive(List<Alarm> alarms)
    {
        List<Alarm> aaa = new ArrayList<>();
        List<Alarm> forDelete = new ArrayList<>();
        for(Alarm ar : alarms)
        {
            if(!ar.getActive())
            {
                Date date = new Date();
                if(((date.getTime()) / (60000)) - ((ar.getDateOfRepetition().get(0).getTime())/(6000)) >= ar.getAlarmType().getMinutes())
                {
                    forDelete.add(ar);
                }
                else
                {
                    aaa.add(ar);
                }
            }
        }
        for(Alarm ar:forDelete)
        {
            this.alarmRepository.delete(ar);
        }
        return aaa;
    }

    public void checkRepetition(Log log, AlarmRule alarmRule)
    {
        if(alarmRule.getRepetition() == 1) {
            Alarm alarm = new Alarm(alarmRule, log.getType(), log.getDescription(), log.getIp(),
                    alarmRule.getRepetition(), true, log.getDate(), log.getHost(), log.getFacility()  );
            alarm.setUsername(log.getTag());
            alarm.addDate(new Date());
            this.alarmRepository.save(alarm);
        }
        else{
            List<Alarm> alarms = this.alarmRepository.findByAlarmRule(alarmRule);
            List<Alarm> notActiveAlarms = checkIfActive(alarms);
            if(notActiveAlarms.size()==0)
            {
                Alarm alarm = new Alarm(alarmRule, log.getType(), log.getDescription(), log.getIp(),
                        alarmRule.getRepetition(), false, log.getDate(), log.getHost(), log.getFacility()  );
                alarm.setUsername(log.getTag());
                alarm.addDate(new Date());
                this.alarmRepository.save(alarm);
            }
            else{
                this.checkAllAlarms(log, alarmRule, notActiveAlarms);
            }
        }
    }


    public void checkAllAlarms(Log log, AlarmRule alarmRule, List<Alarm> notActiveAlarms)
    {
        Boolean flag = false;
        for(Alarm a: notActiveAlarms) {
            Boolean flag1 = true;
            if (!alarmRule.getSameType() && a.getType().equals(log.getType())) flag1 = false;

            if (!alarmRule.getSameHost() && a.getHost().equals(log.getHost())) flag1 = false;

            if (!alarmRule.getSameIpAddress() && a.getIpAdress().equals(log.getIp())) flag1 = false;

            if (!alarmRule.getSameUsername() && a.getUsername().equals(log.getTag())) flag1 = false;

            if (!alarmRule.getSameDate() && a.getDate().equals(log.getDate())) flag1 = false;

            if (!alarmRule.getSameFacility() && a.getFacility().equals(log.getFacility())) flag1 = false;


            if (flag1) {
                flag = true;
                a.addDate(new Date());
                if (a.getDateOfRepetition().size() == alarmRule.getRepetition()) a.setActive(true);
                this.alarmRepository.save(a);
            }
        }
        if(!flag)
        {
            Alarm alarm = new Alarm(alarmRule, log.getType(), log.getDescription(), log.getIp(),
                    alarmRule.getRepetition(), false, log.getDate(), log.getHost(), log.getFacility() );
            alarm.setUsername(log.getTag());
            alarm.addDate(new Date());
            this.alarmRepository.save(alarm);
        }
    }






}
