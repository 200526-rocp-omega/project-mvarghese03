package com.revature.templates;

import java.util.Objects;

import com.revature.models.AccountStatus;
import com.revature.models.AccountType;

public class AccountUserTemplate {
	 private int accountId; // primary key
	  private double balance;  // not null
	  private AccountStatus status;
	  private AccountType type;
	  private int userid;
	
	  public AccountUserTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AccountUserTemplate [accountId=" + accountId + ", balance=" + balance + ", status=" + status + ", type="
				+ type + ", userid=" + userid + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, balance, status, type, userid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountUserTemplate)) {
			return false;
		}
		AccountUserTemplate other = (AccountUserTemplate) obj;
		return accountId == other.accountId
				&& Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& Objects.equals(status, other.status) && Objects.equals(type, other.type) && userid == other.userid;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public AccountUserTemplate(int accountId, double balance, AccountStatus status, AccountType type, int userid) {
		super();
		this.accountId = accountId;
		this.balance = balance;
		this.status = status;
		this.type = type;
		this.userid = userid;
	}
	






}
