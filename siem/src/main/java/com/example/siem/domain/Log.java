package com.example.siem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by djuro on 3/31/2018.
 */

@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "logs")
@CompoundIndex(def = "{'date':1, 'type':-1}", name = "compound_index")
public class Log
{
    @Id
    private String id;

    private String type;

    private String description;

    private Date date;

    private String ip;

    private String host;

    private String facility;

    private String tag;

    private String agentName;

    public Log(String type, String description, String date, String ip , String host, String facility, String tag, String agentName) throws
            ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        this.date = formatter.parse(date);
        this.type = type;
        this.description = description;
        this.ip = ip;
        this.host = host;
        this.tag = tag;
        this.facility = facility;
        this.agentName = agentName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return String.format(
                "Log[id=%s, type='%s', description='%s', date='%s']",
                id, type, description, date);
    }


}
