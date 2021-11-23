package com.revature.dto;

import java.util.Objects;

public class UpdateReimbursementDTO {
	
	private int reimbId;
	private String reimbResolved;
	private String reimbStatus;
	private int reimbResolver;
	
	public UpdateReimbursementDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdateReimbursementDTO(int reimbId, String reimbResolved, String reimbStatus, int reimbResolver) {
		super();
		this.reimbId = reimbId;
		this.reimbResolved = reimbResolved;
		this.reimbStatus = reimbStatus;
		this.reimbResolver = reimbResolver;
	}

	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}

	public String getReimbResolved() {
		return reimbResolved;
	}

	public void setReimbResolved(String reimbResolved) {
		this.reimbResolved = reimbResolved;
	}

	public String getReimbStatus() {
		return reimbStatus;
	}

	public void setReimbStatus(String reimbStatus) {
		this.reimbStatus = reimbStatus;
	}

	public int getReimbResolver() {
		return reimbResolver;
	}

	public void setReimbResolver(int reimbResolver) {
		this.reimbResolver = reimbResolver;
	}

	@Override
	public int hashCode() {
		return Objects.hash(reimbId, reimbResolved, reimbResolver, reimbStatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateReimbursementDTO other = (UpdateReimbursementDTO) obj;
		return reimbId == other.reimbId && Objects.equals(reimbResolved, other.reimbResolved)
				&& reimbResolver == other.reimbResolver && Objects.equals(reimbStatus, other.reimbStatus);
	}

	@Override
	public String toString() {
		return "UpdateReimbursementDTO [reimbId=" + reimbId + ", reimbResolved=" + reimbResolved + ", reimbStatus="
				+ reimbStatus + ", reimbResolver=" + reimbResolver + "]";
	}
	
}
