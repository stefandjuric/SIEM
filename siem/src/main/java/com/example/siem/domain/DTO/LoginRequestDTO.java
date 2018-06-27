package com.example.siem.domain.DTO;

        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.Setter;

/**
 * Created by djuro on 11/4/2017.
 */
@Getter
@Setter
@AllArgsConstructor
public class LoginRequestDTO
{
    private String username;
    private String password;
}
