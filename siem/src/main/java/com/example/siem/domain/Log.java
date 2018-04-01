package com.example.siem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by djuro on 3/31/2018.
 */
@Entity
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@Getter
@Setter
@Table(name = "log")
public class Log
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "log_type")
    private String type;

    @Column(name = "log_description")
    private String description;

    @Column(name = "log_date")
    private Date date;

    public Log(String type, String description, Date date)
    {
        this.type = type;
        this.description = description;
        this.date = date;
    }
}
