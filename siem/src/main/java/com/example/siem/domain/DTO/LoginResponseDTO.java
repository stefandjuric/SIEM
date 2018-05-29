package com.example.siem.domain.DTO;

import com.example.siem.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by djuro on 11/14/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO
{
    private String token;
    private Integer id;
    private String username;
    private String role;

    public LoginResponseDTO(User user)
    {
        this.token = user.getPassword();
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getFirstUserAuthority().getAuthority().getName();
    }
}
