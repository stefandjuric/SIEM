package com.example.siem.service;


import com.example.siem.domain.DTO.LoginResponseDTO;
import com.example.siem.domain.DTO.RegisterUserDTO;
import com.example.siem.domain.User;

import java.util.List;


public interface UserService {
    User save(User user);

    LoginResponseDTO registerUser(RegisterUserDTO registerUser);

    User getUserById(String userId);

    User getUserByUsername(String username);
}
