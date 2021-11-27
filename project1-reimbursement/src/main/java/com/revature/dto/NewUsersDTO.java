package com.revature.dto;

import java.util.Objects;

public class NewUsersDTO {
	
	private String ersUsername;
	private String ersPassword;
	private String ersFirstName;
	private String ersLastName;
	private String ersEmail;
	private String ersRole;
	
	public NewUsersDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewUsersDTO(String ersUsername, String ersPassword, String ersFirstName, String ersLastName, String ersEmail,
			String ersRole) {
		super();
		this.ersUsername = ersUsername;
		this.ersPassword = ersPassword;
		this.ersFirstName = ersFirstName;
		this.ersLastName = ersLastName;
		this.ersEmail = ersEmail;
		this.ersRole = ersRole;
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

	public String getErsFirstName() {
		return ersFirstName;
	}

	public void setErsFirstName(String ersFirstName) {
		this.ersFirstName = ersFirstName;
	}

	public String getErsLastName() {
		return ersLastName;
	}

	public void setErsLastName(String ersLastName) {
		this.ersLastName = ersLastName;
	}

	public String getErsEmail() {
		return ersEmail;
	}

	public void setErsEmail(String ersEmail) {
		this.ersEmail = ersEmail;
	}

	public String getErsRole() {
		return ersRole;
	}

	public void setErsRole(String ersRole) {
		this.ersRole = ersRole;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ersEmail, ersFirstName, ersLastName, ersPassword, ersRole, ersUsername);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewUsersDTO other = (NewUsersDTO) obj;
		return Objects.equals(ersEmail, other.ersEmail) && Objects.equals(ersFirstName, other.ersFirstName)
				&& Objects.equals(ersLastName, other.ersLastName) && Objects.equals(ersPassword, other.ersPassword)
				&& Objects.equals(ersRole, other.ersRole) && Objects.equals(ersUsername, other.ersUsername);
	}

	@Override
	public String toString() {
		return "NewUsersDTO [ersUsername=" + ersUsername + ", ersPassword=" + ersPassword + ", ersFirstName="
				+ ersFirstName + ", ersLastName=" + ersLastName + ", ersEmail=" + ersEmail + ", ersRole=" + ersRole
				+ "]";
	}
		
}
