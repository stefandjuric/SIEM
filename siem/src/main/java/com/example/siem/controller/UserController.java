package com.example.siem.controller;

import com.example.siem.domain.DTO.ChangePasswordDTO;
import com.example.siem.domain.DTO.LoginRequestDTO;
import com.example.siem.domain.DTO.LoginResponseDTO;
import com.example.siem.domain.DTO.RegisterUserDTO;
import com.example.siem.domain.User;
import com.example.siem.security.TokenUtils;
import com.example.siem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by djuro on 11/4/2017.
 */

/**
 * Kontroler za rad sa svi tipovima korisnika
 */
@RestController
@CrossOrigin
public class UserController
{
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    TokenUtils tokenUtils;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());
            String userToken = tokenUtils.generateToken(details);
            User user = this.userService.getUserByUsername(loginDTO.getUsername());
            System.out.println(user.getUserAuthorities().iterator().next().getAuthority().getName());
            return new ResponseEntity<>(new LoginResponseDTO(userToken, user.getId(),loginDTO.getUsername(),user.getUserAuthorities().iterator().next().getAuthority().getName()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Invalid login", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/registerUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegisterUserDTO registerUser)
    {
        System.out.println("usaoooooooooo");
        LoginResponseDTO responseDTO = this.userService.registerUser(registerUser);
        if(responseDTO == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO)
    {
        LoginResponseDTO user = this.userService.changePassord(changePasswordDTO);
        if(user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
