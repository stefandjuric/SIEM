package com.example.siem.domain.DTO;

import com.example.siem.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by djuro on 11/19/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO
{
    private String username;
    private String password;
    private String role;
    private String firstname;
    private String lastname;
    private String institutionName;
    private Address address;
    private String firmName;
    private String firmDescription;
}
