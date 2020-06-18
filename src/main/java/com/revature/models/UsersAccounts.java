package com.revature.models;

import java.util.Objects;

public class UsersAccounts {
	private int userId;
	private int accountId;
	public UsersAccounts() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UsersAccounts(int userId, int accountId) {
		super();
		this.userId = userId;
		this.accountId = accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(accountId, userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UsersAccounts)) {
			return false;
		}
		UsersAccounts other = (UsersAccounts) obj;
		return accountId == other.accountId && userId == other.userId;
	}
	@Override
	public String toString() {
		return "UsersAccounts [userId=" + userId + ", accountId=" + accountId + "]";
	}

	

}
