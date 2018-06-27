package com.example.siem.service.implementation;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.siem.domain.AgentData;
import com.example.siem.domain.Alarm;
import com.example.siem.domain.AlarmRule;
import com.example.siem.domain.Log;
import com.example.siem.repository.AgentDataRepository;
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

    private AgentDataRepository agentDataRepository;

    @Autowired
    public SiemAgentServiceImpl(LogRepository logRepository,
                                MongoTemplate mongoTemplate,
                                AlarmRuleRepository alarmRuleRepository,
                                AlarmRepository alarmRepository,
                                AgentDataRepository agentDataRepository)
    {
        this.logRepository = logRepository;
        this.mongoTemplate = mongoTemplate;
        this.alarmRuleRepository = alarmRuleRepository;
        this.alarmRepository = alarmRepository;
        this.agentDataRepository = agentDataRepository;

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

    @Override
    public void saveAgentInformation(String information) {
        information = information.replace('+', ' ');
        information = information.replace("%3A", ":");
        information = information.replace("%5B", "{");
        information = information.replace("%5D", "}");
        information = information.replace( "%5C", "/");
        information = information.replace( "%2C", ",");
        String[] elements = information.split("&");
        AgentData ad = new AgentData();
        ad.setName(elements[0].split("=")[1]);
        String filePaths = elements[1].split("=")[1];
        ad.setFilePaths(filePaths.split(","));
        String types = elements[2].split("=")[1];
        ad.setTypes(types.split(","));
        ad.setEnabled(Boolean.parseBoolean(elements[3].split("=")[1]));
        ad.setRole(elements[4].split("=")[1]);
        String ports = elements[5].split("=")[1];
        ad.setPorts(ports.split(","));
        ad.setPort(elements[6].split("=")[1]);
        ad.setBatch(elements[7].split("=")[1]);
        ad.setLevel(elements[8].split("=")[1]);
        agentDataRepository.save(ad);
    }

    @Override
    public List<AgentData> getAllAgents() {
        return agentDataRepository.findAll();
    }

    @Override
    public AgentData getAgent(String id) {
        return agentDataRepository.findOne(id);
    }

    public void addAlarm(Log log)
    {
        List<AlarmRule> alarmRules = this.alarmRuleRepository.findAll();
        for(AlarmRule ar : alarmRules)
        {
            if(ar.getTypeFlag() &&  !ar.getDescriptionFlag() && !ar.getIpAddressFlag()) this.checkType(log, ar); //+++

            if(!ar.getTypeFlag() &&  ar.getDescriptionFlag() && !ar.getIpAddressFlag()) this.checkDescription(log, ar);

            if(!ar.getTypeFlag() &&  !ar.getDescriptionFlag() && ar.getIpAddressFlag()) this.checkIpAddress(log, ar);

            if(ar.getTypeFlag() &&  ar.getDescriptionFlag() && !ar.getIpAddressFlag()) this.checkTypeAndDescription(log, ar);

            if(ar.getTypeFlag() &&  !ar.getDescriptionFlag() && ar.getIpAddressFlag()) this.checkTypeAndIpAddress(log, ar);

            if(!ar.getTypeFlag() &&  ar.getDescriptionFlag() && ar.getIpAddressFlag()) this.checkDecriptionAndIpAddress(log, ar);

            if(ar.getTypeFlag() &&  ar.getDescriptionFlag() && ar.getIpAddressFlag()) this.checkTypeAndDecriptionAndIpAddress(log, ar);

        }
    }

    public void checkType(Log log, AlarmRule alarmRule)
    {
        if(alarmRule.getType().equals(log.getType()))
        {
            this.checkRepetition(log,alarmRule);
        }
    }


    public void checkDescription(Log log, AlarmRule alarmRule)
    {
        System.out.println("//////////////////////////////////////////////////");
        System.out.println(alarmRule.getDescription());
        System.out.println(log.getDescription());
        if(alarmRule.getDescription().equals(log.getDescription()))
        {
            System.out.println("---------------------------------------");
            System.out.println(log.getDescription()); //--------------------------------------------------
            System.out.println("---------------------------------------");
            this.checkRepetition(log,alarmRule);
        }
    }

    public void checkIpAddress(Log log, AlarmRule alarmRule)
    {
        if(alarmRule.getIpAddress().equals(log.getIp()))
        {
            this.checkRepetition(log,alarmRule);
        }
    }

    public void checkTypeAndDescription(Log log, AlarmRule alarmRule)
    {
        if(alarmRule.getType().equals(log.getType()) && alarmRule.getDescription().equals(log.getDescription()))
        {
            this.checkRepetition(log,alarmRule);
        }
    }

    public void checkTypeAndIpAddress(Log log, AlarmRule alarmRule)
    {
        if(alarmRule.getType().equals(log.getType()) && alarmRule.getIpAddress().equals(log.getIp()))
        {
            this.checkRepetition(log,alarmRule);
        }
    }

    public void checkDecriptionAndIpAddress(Log log, AlarmRule alarmRule)
    {
        if(alarmRule.getDescription().equals(log.getDescription()) && alarmRule.getIpAddress().equals(log.getIp()))
        {
            this.checkRepetition(log,alarmRule);
        }
    }

    public void checkTypeAndDecriptionAndIpAddress(Log log, AlarmRule alarmRule)
    {
        if(alarmRule.getType().equals(log.getType()) && alarmRule.getDescription().equals(log.getDescription()) && alarmRule.getIpAddress().equals(log.getIp()))
        {
            this.checkRepetition(log,alarmRule);
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
            Alarm alarm = new Alarm(alarmRule, log.getType(), log.getDescription(), log.getIp(), alarmRule.getRepetition(), true );
            alarm.setUsername(log.getTag());
            alarm.addDate(new Date());
            this.alarmRepository.save(alarm);
        }
        else{
            List<Alarm> alarms = this.alarmRepository.findByAlarmRule(alarmRule);
            List<Alarm> notActiveAlarms = checkIfActive(alarms);
            if(notActiveAlarms.size()==0)
            {
                Alarm alarm = new Alarm(alarmRule, log.getType(), log.getDescription(), log.getIp(), alarmRule.getRepetition(), false );
                alarm.setUsername(log.getTag());
                alarm.addDate(new Date());
                this.alarmRepository.save(alarm);
            }
            else{
                if(!alarmRule.getSameIpAddress() && !alarmRule.getSameUsername()) notIpAddressAndNotUsername(log, alarmRule, notActiveAlarms);

                if(alarmRule.getSameIpAddress() && !alarmRule.getSameUsername()) sameIpAddressAndNotUsername(log, alarmRule, notActiveAlarms);

                if(!alarmRule.getSameIpAddress() && alarmRule.getSameUsername()) notIpAddressAndSameUsername(log, alarmRule, notActiveAlarms);

                if(alarmRule.getSameIpAddress() && alarmRule.getSameUsername()) sameIpAddressAndSameUsername(log, alarmRule, notActiveAlarms);
            }
        }
    }

    public void notIpAddressAndNotUsername(Log log, AlarmRule alarmRule, List<Alarm> notActiveAlarms)
    {
        Alarm alarm = notActiveAlarms.get(0);
        alarm.addDate(new Date());
        if(alarm.getDateOfRepetition().size() == alarmRule.getRepetition()) alarm.setActive(true);
        this.alarmRepository.save(alarm);
    }

    public void sameIpAddressAndNotUsername(Log log, AlarmRule alarmRule, List<Alarm> notActiveAlarms)
    {
        Boolean flag = false;
        for(Alarm a: notActiveAlarms)
        {
            if(a.getIpAdress().equals(log.getIp())){
                flag = true;
                a.addDate(new Date());
                if(a.getDateOfRepetition().size() == alarmRule.getRepetition()) a.setActive(true);
                this.alarmRepository.save(a);
            }
        }
        if(!flag){
            Alarm alarm = new Alarm(alarmRule, log.getType(), log.getDescription(), log.getIp(), alarmRule.getRepetition(), false );
            alarm.setUsername(log.getTag());
            alarm.addDate(new Date());
            this.alarmRepository.save(alarm);
        }
    }

    public void notIpAddressAndSameUsername(Log log, AlarmRule alarmRule, List<Alarm> notActiveAlarms)
    {
        Boolean flag = false;
        for(Alarm a: notActiveAlarms)
        {
            if(a.getUsername().equals(log.getTag())){
                flag = true;
                a.addDate(new Date());
                if(a.getDateOfRepetition().size() == alarmRule.getRepetition()) a.setActive(true);
                this.alarmRepository.save(a);
            }
        }
        if(!flag){
            Alarm alarm = new Alarm(alarmRule, log.getType(), log.getDescription(), log.getIp(), alarmRule.getRepetition(), false );
            alarm.setUsername(log.getTag());
            alarm.addDate(new Date());
            this.alarmRepository.save(alarm);
        }
    }


    public void sameIpAddressAndSameUsername(Log log, AlarmRule alarmRule, List<Alarm> notActiveAlarms)
    {
        Boolean flag = false;
        for(Alarm a: notActiveAlarms)
        {
            if(a.getUsername().equals(log.getTag()) && a.getIpAdress().equals(log.getIp())){
                flag = true;
                a.addDate(new Date());
                if(a.getDateOfRepetition().size() == alarmRule.getRepetition()) a.setActive(true);
                this.alarmRepository.save(a);
            }
        }
        if(!flag){
            Alarm alarm = new Alarm(alarmRule, log.getType(), log.getDescription(), log.getIp(), alarmRule.getRepetition(), false );
            alarm.setUsername(log.getTag());
            alarm.addDate(new Date());
            this.alarmRepository.save(alarm);
        }
    }



}
