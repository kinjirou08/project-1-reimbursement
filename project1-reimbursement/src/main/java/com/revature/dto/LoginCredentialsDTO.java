package com.revature.dto;

import java.util.Objects;

public class LoginCredentialsDTO {
	
	private String ersUsername;
	private String ersPassword;
	
	public LoginCredentialsDTO () {		
	}

	public LoginCredentialsDTO(String ersUsername, String ersPassword) {
		super();
		this.ersUsername = ersUsername;
		this.ersPassword = ersPassword;
	}

	public String getErsUsername() {
		return ersUsername;
	}

	public void setErsUsername(String ersUsername) {
		this.ersUsername = ersUsername;
	}

	public String getErsPassword() {
		return ersPassword;
	}

	public void setErsPassword(String ersPassword) {
		this.ersPassword = ersPassword;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ersPassword, ersUsername);
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
		return Objects.equals(ersPassword, other.ersPassword) && Objects.equals(ersUsername, other.ersUsername);
	}

	@Override
	public String toString() {
		return "LoginCredentialsDTO [ers_username=" + ersUsername + ", ers_password=" + ersPassword + "]";
	}	
	
}
