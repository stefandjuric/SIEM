package com.example.siem.service.implementation;

import com.example.siem.domain.*;
import com.example.siem.domain.DTO.ChangePasswordDTO;
import com.example.siem.domain.DTO.LoginRequestDTO;
import com.example.siem.domain.DTO.LoginResponseDTO;
import com.example.siem.domain.DTO.RegisterUserDTO;
import com.example.siem.repository.*;
import com.example.siem.security.TokenUtils;
import com.example.siem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserAuthorityRepository userAuthorityRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    OrdinaryUserRepository odOrdinaryUserRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    TokenUtils tokenUtils;


    @Override
    public User save(User user) {
        this.userRepository.save(user);
        return user;
    }

    @Override
    public LoginResponseDTO registerUser(RegisterUserDTO registerUser)
    {
        registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        Authority authority = this.authorityRepository.findByName(registerUser.getRole());  //u bazi se nalaze predefinisane uloge
        User user = new User(registerUser.getUsername(),registerUser.getPassword());
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setAuthority(authority);
        this.userAuthorityRepository.save(userAuthority);
        switch (registerUser.getRole()) {
            case "ROLE_ADMIN":
            {

                Admin admin = new Admin(registerUser);
                admin.addUserAuthority(userAuthority);
                this.adminRepository.save(admin);
                userAuthority.setUser(admin);
                this.userAuthorityRepository.save(userAuthority);
                user.setId(admin.getId());
                user.addUserAuthority(userAuthority);
                break;
            }
            case "ORDINARY_USER":
            {
                OrdinaryUser ordinaryUser = new OrdinaryUser(registerUser);
                ordinaryUser.addUserAuthority(userAuthority);
                this.odOrdinaryUserRepository.save(ordinaryUser);
                userAuthority.setUser(ordinaryUser);
                this.userAuthorityRepository.save(userAuthority);
                user.setId(ordinaryUser.getId());
                user.addUserAuthority(userAuthority);
                break;
            }
            default:
                break;
        }
        return new LoginResponseDTO(user);
    }

    @Override
    public User getUserById(Integer userId)
    {
        User user = this.userRepository.getOne(userId);
        return user;
    }

    @Override
    public User getUserByUsername(String username)
    {
        User user = null;
        user = this.userRepository.findByUsername(username);
        return user;
    }

    @Override
    public LoginResponseDTO changePassord(ChangePasswordDTO changePasswordDTO)
    {
        User user = null;
        user = this.userRepository.findOne(changePasswordDTO.getUserId());
        if(user!=null)
        {
            try {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        user.getUsername(), changePasswordDTO.getOldPassword());
                Authentication authentication = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetails details = userDetailsService.loadUserByUsername(user.getUsername());
                String userToken = tokenUtils.generateToken(details);
                user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                this.userRepository.save(user);
                String userToken1 = tokenUtils.generateToken(details);
                return new LoginResponseDTO(userToken1, user.getId(),user.getUsername(),user.getUserAuthorities().iterator().next().getAuthority().getName());
            } catch (Exception ex) {
               return null;
            }
        }
        else{
            return null;
        }
    }

}
