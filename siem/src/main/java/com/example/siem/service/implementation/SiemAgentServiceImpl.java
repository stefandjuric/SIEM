package com.example.siem.service.implementation;

import com.example.siem.domain.Log;
import com.example.siem.repository.LogRepository;
import com.example.siem.service.SiemAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Log saveLog(String log)
    {
        //TO DO ovde parsiraj tesk i sacuvaj LOG u bazu
        return new Log();
    }
}
