package com.example.siem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private Integer repetition;

    private Integer minutes;

    private Boolean typeFlag;

    private Boolean descriptionFlag;

    private Boolean ipAddressFlag;

    public AlarmRule(String type, String description, String ipAddress, Integer repetition,
                     Integer minutes, Boolean typeFlag, Boolean descriptionFlag, Boolean ipAddressFlag){
        this.type = type;
        this.description = description;
        this.ipAddress = ipAddress;
        this.repetition = repetition;
        this.minutes = minutes;
        this.typeFlag = typeFlag;
        this.descriptionFlag = descriptionFlag;
        this.ipAddressFlag = ipAddressFlag;
    }
}
