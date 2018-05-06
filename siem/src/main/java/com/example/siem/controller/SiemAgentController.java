package com.example.siem.controller;

import com.example.siem.domain.DTO.SearchByDateDTO;
import com.example.siem.domain.Log;
import com.example.siem.service.SiemAgentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

/**
 * Created by djuro on 3/31/2018.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/agent")
public class SiemAgentController
{

    @Autowired
    SiemAgentService siemAgentService;

    @RequestMapping(value = "/saveLog", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Log> addLog(@RequestBody String log) throws ParseException {

        System.out.println("Usao u post metodu");
        Log logObject = this.siemAgentService.saveLog(log);
        if(logObject == null)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(logObject, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/getLogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Log>> getLogs()
    {
        List<Log> logs = this.siemAgentService.getLogs();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @RequestMapping(value = "/getLogsByType/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Log>> getLogsByType(@PathVariable("type") String type)
    {
        List<Log> logs = this.siemAgentService.searchByType(type);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @RequestMapping(value = "/getLogsByDate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Log>> getLogsByDate(@RequestBody SearchByDateDTO dto)
    {
        List<Log> logs = this.siemAgentService.searchByDate(dto.getDate1(), dto.getDate2());
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @RequestMapping(value = "/welcome")
    public String hello()
    {
        return "Hello world!!!";
    }
}

