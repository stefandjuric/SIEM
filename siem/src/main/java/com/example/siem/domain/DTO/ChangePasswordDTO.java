package com.example.siem.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by djuro on 6/5/2018.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

    private Integer userId;
    private String role;
    private String oldPassword;
    private String newPassword;
}
