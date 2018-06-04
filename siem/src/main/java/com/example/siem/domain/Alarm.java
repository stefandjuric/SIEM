package com.example.siem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by djuro on 5/29/2018.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "alarms")
public class Alarm {

    @Id
    private String id;

    private AlarmRule alarmType;

    private String type;

    private String description;

    private String ipAdress;

    private Integer number;

    private List<Date> dateOfRepetition;

    private Boolean active;

    private String username;

    public Alarm(AlarmRule alarmType, String type, String description, String ipAdress,
                 Integer number, Boolean active) {
        this.alarmType = alarmType;
        this.type = type;
        this.description = description;
        this.ipAdress = ipAdress;
        this.number = number;
        this.dateOfRepetition = new ArrayList<>();
        this.active = active;
        this.username = "";
    }

    public void addDate(Date date)
    {
        this.dateOfRepetition.add(date);
    }
}
