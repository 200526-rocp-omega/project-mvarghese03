package com.revature.templates;

import java.util.Objects;

public class InterestAccrualTemplate {
	private int numOfMonths;

	public InterestAccrualTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InterestAccrualTemplate(int numOfMonths) {
		super();
		this.numOfMonths = numOfMonths;
	}

	public int getNumOfMonths() {
		return numOfMonths;
	}

	public void setNumOfMonths(int numOfMonths) {
		this.numOfMonths = numOfMonths;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numOfMonths);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof InterestAccrualTemplate)) {
			return false;
		}
		InterestAccrualTemplate other = (InterestAccrualTemplate) obj;
		return numOfMonths == other.numOfMonths;
	}

	@Override
	public String toString() {
		return "InterestAccrualTemplate [numOfMonths=" + numOfMonths + "]";
	}
	
	

}
