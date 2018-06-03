package com.example.siem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
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

    public Log(String type, String description, Date date)
    {
        this.type = type;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format(
                "Log[id=%s, type='%s', description='%s', date='%s']",
                id, type, description, date);
    }
}
