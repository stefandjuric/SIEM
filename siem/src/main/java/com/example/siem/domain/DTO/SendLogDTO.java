package com.example.siem.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendLogDTO
{
    private String type;
    private String description;
    private String date;
    private String ip;
    private String host;
    private String facility;
    private String tag;
}

