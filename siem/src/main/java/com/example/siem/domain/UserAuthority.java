package com.example.siem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document(collection = "user_suthority")
public class UserAuthority {
	@Id
	private String id;

	@JsonIgnore
	private User user;
	
	@JsonIgnore
	private Authority authority;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	/*
	//ovo je greska vjerovatno
	public UserAuthority(User user, Authority authority)
	{
		this.user = user;
		this.authority = authority;
	}
	*/
	public UserAuthority() {};
}
