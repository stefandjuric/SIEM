package com.example.siem.controller;

import com.example.siem.domain.Log;
import com.example.siem.service.SiemAgentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

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

    @RequestMapping(value = "/saveLog", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Log> makeBill(@RequestBody String log)
    {
        Log logObject = this.siemAgentService.saveLog(log);
        if(logObject == null)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(logObject, HttpStatus.CREATED);
    }
}
