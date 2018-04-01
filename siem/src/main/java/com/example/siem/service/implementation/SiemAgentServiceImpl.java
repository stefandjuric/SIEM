package com.example.siem.service.implementation;

import com.example.siem.domain.Log;
import com.example.siem.repository.LogRepository;
import com.example.siem.service.SiemAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
