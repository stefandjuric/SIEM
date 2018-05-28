package com.example.siem.domain;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class User
{
    @Id
    private String id;

    private String username;

    private String password;

    private Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>();

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Set<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public void addUserAuthority(UserAuthority userAuthority)
    {
        userAuthorities.add(userAuthority);
    }

    public UserAuthority getFirstUserAuthority()
    {
        if(!this.userAuthorities.isEmpty()){
            return this.userAuthorities.iterator().next();
        }
        return null;
    }
}
