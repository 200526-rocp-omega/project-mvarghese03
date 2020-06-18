package com.revature.templates;

import java.util.Objects;

public class WithdrawTemplate {

	private int accountId;
	private double amount;
	
	public WithdrawTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WithdrawTemplate(int accountId, double amount) {
		super();
		this.accountId = accountId;
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof WithdrawTemplate)) {
			return false;
		}
		WithdrawTemplate other = (WithdrawTemplate) obj;
		return accountId == other.accountId && Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount);
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "WithdrawTemplate [accountId=" + accountId + ", amount=" + amount + "]";
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


	
	
}
