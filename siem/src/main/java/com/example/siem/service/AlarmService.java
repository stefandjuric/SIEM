package com.example.siem.service;

import com.example.siem.domain.Alarm;

import java.util.List;

/**
 * Created by djuro on 6/1/2018.
 */
public interface AlarmService {

    List<Alarm> getAllAlarms();
}
