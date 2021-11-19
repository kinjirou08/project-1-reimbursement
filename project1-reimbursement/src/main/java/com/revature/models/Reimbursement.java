package com.revature.models;

import java.util.Arrays;
import java.util.Objects;

public class Reimbursement {
	
	private int reimbId;
	private double reimbAmount;
	private String reimbSubmitted;
	private String reimbResolved;
	private String reimbStatus; // Default = "Pending" -- other options: "Approved" AND "Rejected"
	private String reimbType; // Food, Lodging, Travel, Other
	private String reimbDescription;
	private byte[] reimbReceipt; 
	private int reimbAuthor;
	private int reimbResolver;
	
	public Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reimbursement(int reimbId, double reimbAmount, String reimbSubmitted, String reimbResolved,
			String reimbStatus, String reimbType, String reimbDescription, byte[] reimbReceipt, int reimbAuthor,
			int reimbResolver) {
		super();
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.reimbSubmitted = reimbSubmitted;
		this.reimbResolved = reimbResolved;
		this.reimbStatus = reimbStatus;
		this.reimbType = reimbType;
		this.reimbDescription = reimbDescription;
		this.reimbReceipt = reimbReceipt;
		this.reimbAuthor = reimbAuthor;
		this.reimbResolver = reimbResolver;
	}

	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
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

	public byte[] getReimbReceipt() {
		return reimbReceipt;
	}

	public void setReimbReceipt(byte[] reimbReceipt) {
		this.reimbReceipt = reimbReceipt;
	}

	public int getReimbAuthor() {
		return reimbAuthor;
	}

	public void setReimbAuthor(int reimbAuthor) {
		this.reimbAuthor = reimbAuthor;
	}

	public int getReimbResolver() {
		return reimbResolver;
	}

	public void setReimbResolver(int reimbResolver) {
		this.reimbResolver = reimbResolver;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(reimbReceipt);
		result = prime * result + Objects.hash(reimbAmount, reimbAuthor, reimbDescription, reimbId, reimbResolved,
				reimbResolver, reimbStatus, reimbSubmitted, reimbType);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		return Double.doubleToLongBits(reimbAmount) == Double.doubleToLongBits(other.reimbAmount)
				&& reimbAuthor == other.reimbAuthor && Objects.equals(reimbDescription, other.reimbDescription)
				&& reimbId == other.reimbId && Arrays.equals(reimbReceipt, other.reimbReceipt)
				&& Objects.equals(reimbResolved, other.reimbResolved) && reimbResolver == other.reimbResolver
				&& Objects.equals(reimbStatus, other.reimbStatus)
				&& Objects.equals(reimbSubmitted, other.reimbSubmitted) && Objects.equals(reimbType, other.reimbType);
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", reimbAmount=" + reimbAmount + ", reimbSubmitted="
				+ reimbSubmitted + ", reimbResolved=" + reimbResolved + ", reimbStatus=" + reimbStatus + ", reimbType="
				+ reimbType + ", reimbDescription=" + reimbDescription + ", reimbReceipt="
				+ Arrays.toString(reimbReceipt) + ", reimbAuthor=" + reimbAuthor + ", reimbResolver=" + reimbResolver
				+ "]";
	}
	
		
}
