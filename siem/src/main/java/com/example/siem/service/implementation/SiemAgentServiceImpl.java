package com.example.siem.service.implementation;

import com.example.siem.domain.Log;
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

    private LogRepository logRepository;

    private MongoTemplate mongoTemplate;

    @Autowired
    public SiemAgentServiceImpl(LogRepository logRepository,
                                MongoTemplate mongoTemplate)
    {
        this.logRepository = logRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Log saveLog(String log) throws ParseException {

        String[] logStr = log.split("#");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String type = logStr[0];
        String description = logStr[1];
        Date date = formatter.parse(logStr[2]);

        Log saved = this.logRepository.save(new Log(type, description, date));


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
}
