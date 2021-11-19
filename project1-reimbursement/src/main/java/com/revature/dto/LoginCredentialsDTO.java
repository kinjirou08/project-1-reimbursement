package com.revature.dto;

import java.util.Objects;

public class LoginCredentialsDTO {
	
	private String ers_username;
	private String ers_password;
	
	public LoginCredentialsDTO () {		
	}

	public LoginCredentialsDTO(String ers_username, String ers_password) {
		super();
		this.ers_username = ers_username;
		this.ers_password = ers_password;
	}

	public String getErs_username() {
		return ers_username;
	}

	public void setErs_username(String ers_username) {
		this.ers_username = ers_username;
	}

	public String getErs_password() {
		return ers_password;
	}

	public void setErs_password(String ers_password) {
		this.ers_password = ers_password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ers_password, ers_username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginCredentialsDTO other = (LoginCredentialsDTO) obj;
		return Objects.equals(ers_password, other.ers_password) && Objects.equals(ers_username, other.ers_username);
	}

	@Override
	public String toString() {
		return "LoginCredentialsDTO [ers_username=" + ers_username + ", ers_password=" + ers_password + "]";
	}	
	
}
