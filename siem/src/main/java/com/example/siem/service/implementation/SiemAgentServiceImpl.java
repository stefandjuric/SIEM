package com.example.siem.service.implementation;

import com.example.siem.domain.Log;
import com.example.siem.repository.LogRepository;
import com.example.siem.service.SiemAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private final LogRepository logRepository;

    @Autowired
    public SiemAgentServiceImpl(LogRepository logRepository)
    {
        this.logRepository = logRepository;
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
}
