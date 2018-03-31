package com.example.siem.service;

import com.example.siem.domain.Log;

/**
 * Created by djuro on 3/31/2018.
 */
public interface SiemAgentService
{
    Log saveLog(String log);
}
