package com.example.siem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by djuro on 5/29/2018.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "alarm_rules")
public class AlarmRule {

    @Id
    private String id;

    private String type;

    private String description;

    private String ipAddress;

    private Date date;

    private String host;

    private String facility;

    private String username;

    private Integer repetition;

    private Integer minutes;

    private Boolean typeFlag;

    private Boolean descriptionFlag;

    private Boolean ipAddressFlag;

    private Boolean dateFlag;

    private Boolean hostFlag;

    private Boolean facilityFlag;

    private Boolean usernameFlag;

    private Boolean sameIpAddress;

    private Boolean sameUsername;

    private Boolean sameType;

    private Boolean sameDescription;

    private Boolean sameDate;

    private Boolean sameHost;

    private Boolean sameFacility;

    public AlarmRule(String type, String description, String ipAddress,
                     Date date, String host, String facility, String username,
                     Integer repetition, Integer minutes, Boolean typeFlag,
                     Boolean descriptionFlag, Boolean ipAddressFlag, Boolean dateFlag,
                     Boolean hostFlag, Boolean facilityFlag, Boolean usernameFlag,
                     Boolean sameIpAddress, Boolean sameUsername, Boolean sameType,
                     Boolean sameDescription, Boolean sameDate, Boolean sameHost,
                     Boolean sameFacility) {
        this.type = type;
        this.description = description;
        this.ipAddress = ipAddress;
        this.date = date;
        this.host = host;
        this.facility = facility;
        this.username = username;
        this.repetition = repetition;
        this.minutes = minutes;
        this.typeFlag = typeFlag;
        this.descriptionFlag = descriptionFlag;
        this.ipAddressFlag = ipAddressFlag;
        this.dateFlag = dateFlag;
        this.hostFlag = hostFlag;
        this.facilityFlag = facilityFlag;
        this.usernameFlag = usernameFlag;
        this.sameIpAddress = sameIpAddress;
        this.sameUsername = sameUsername;
        this.sameType = sameType;
        this.sameDescription = sameDescription;
        this.sameDate = sameDate;
        this.sameHost = sameHost;
        this.sameFacility = sameFacility;
    }
}
