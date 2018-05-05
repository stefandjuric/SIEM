package com.example.siem.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by djuro on 5/3/2018.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchByDateDTO
{
    Date date1;
    Date date2;
}
