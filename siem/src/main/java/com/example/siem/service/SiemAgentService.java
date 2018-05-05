package com.example.siem.service;

import com.example.siem.domain.Log;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by djuro on 3/31/2018.
 */
public interface SiemAgentService
{
    Log saveLog(String log) throws ParseException;

    List<Log> getLogs();

    List<Log> searchByType(String type);

    List<Log> searchByDate(Date from, Date to);
}
