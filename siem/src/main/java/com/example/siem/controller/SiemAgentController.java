package com.example.siem.controller;

import com.example.siem.domain.AgentData;
import com.example.siem.domain.Alarm;
import com.example.siem.domain.DTO.SearchByDateDTO;
import com.example.siem.domain.DTO.SendLogDTO;
import com.example.siem.domain.Log;
import com.example.siem.service.SiemAgentService;
import com.mongodb.util.JSON;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.net.*;
/**
 * Created by djuro on 3/31/2018.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/agent")
public class SiemAgentController
{

    @Autowired
    SiemAgentService siemAgentService;

    @RequestMapping(value = "/saveLog", method = RequestMethod.POST
            ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentData> addLog(@RequestBody byte[] s) throws ParseException {

        String s2 = new String(s);

        Log newLog = this.siemAgentService.saveLog(s2);
        //System.out.println(s2);

        /*Log log = new Log(logDto.getType(),logDto.getDescription(),logDto.getDate(),logDto.getIp(),
                logDto.getHost(),logDto.getFacility(),logDto.getTag());
        Log logObject = this.siemAgentService.saveLog(log);
        if(logObject == null)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        */
        String agentName = newLog.getAgentName();
        AgentData agentData = this.siemAgentService.getAgentDataByName(agentName);

        return new ResponseEntity<>(agentData, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/getLogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Log>> getLogs()
    {
        System.out.print("usao u getLogs");
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

    @RequestMapping(value = "/getLogsByRegex", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Log>> getLogsByRegex(@RequestBody String regex)
    {
        //regex = regex.replace('%',' ');
        List<Log> logs = this.siemAgentService.searchByRegex(regex);
        if(logs == null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(logs, HttpStatus.OK);
    }


    @RequestMapping(value = "/sendAgentData", method = RequestMethod.POST
            ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendAgentData(@RequestBody byte[] s) throws ParseException {

        String s2 = new String(s);

        this.siemAgentService.saveAgentInformation(s2);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @RequestMapping(value = "/getAgents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AgentData>> getAgents()
    {
        List<AgentData> agents = this.siemAgentService.getAllAgents();
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAgent/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentData> getAgent(@PathVariable("id") String id)
    {
        AgentData ad = this.siemAgentService.getAgent(id);
        return new ResponseEntity<>(ad, HttpStatus.OK);
    }


    @RequestMapping(value = "/addAgentData", method = RequestMethod.POST
            ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentData> addAgentData(@RequestBody AgentData agentData){

        AgentData saved = this.siemAgentService.addAgentData(agentData);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

}

