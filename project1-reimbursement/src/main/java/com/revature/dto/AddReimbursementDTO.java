package com.revature.dto;

import java.util.Objects;

public class AddReimbursementDTO {
	
	private double reimbAmount;
	private String reimbSubmitted;
	private String reimbType;
	private String reimbDescription;
	
	public AddReimbursementDTO() {
		super();
		}

	public AddReimbursementDTO(double reimbAmount, String reimbSubmitted, String reimbType, String reimbDescription) {
		super();
		this.reimbAmount = reimbAmount;
		this.reimbSubmitted = reimbSubmitted;
		this.reimbType = reimbType;
		this.reimbDescription = reimbDescription;
	}

	public double getReimbAmount() {
		return reimbAmount;
	}

	public void setReimbAmount(double reimbAmount) {
		this.reimbAmount = reimbAmount;
	}

	public String getReimbSubmitted() {
		return reimbSubmitted;
	}

	public void setReimbSubmitted(String reimbSubmitted) {
		this.reimbSubmitted = reimbSubmitted;
	}

	public String getReimbType() {
		return reimbType;
	}

	public void setReimbType(String reimbType) {
		this.reimbType = reimbType;
	}

	public String getReimbDescription() {
		return reimbDescription;
	}

	public void setReimbDescription(String reimbDescription) {
		this.reimbDescription = reimbDescription;
	}

	@Override
	public int hashCode() {
		return Objects.hash(reimbAmount, reimbDescription, reimbSubmitted, reimbType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddReimbursementDTO other = (AddReimbursementDTO) obj;
		return Double.doubleToLongBits(reimbAmount) == Double.doubleToLongBits(other.reimbAmount)
				&& Objects.equals(reimbDescription, other.reimbDescription)
				&& Objects.equals(reimbSubmitted, other.reimbSubmitted) && Objects.equals(reimbType, other.reimbType);
	}

	@Override
	public String toString() {
		return "AddReimbursementDTO [reimbAmount=" + reimbAmount + ", reimbSubmitted=" + reimbSubmitted + ", reimbType="
				+ reimbType + ", reimbDescription=" + reimbDescription + "]";
	}

	
}

