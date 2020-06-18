package com.revature.controllers;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.UsersAccounts;
import com.revature.services.AccountService;
import com.revature.services.UserAccountService;

public class UserAccountController {
	
	
	private final UserAccountService useraccountService = new UserAccountService();
	
	public List<UsersAccounts> findAccountById(int accountID) {
		return useraccountService.findByAccountID(accountID);
	}
	
	public List<UsersAccounts> findByUserID(int userID) {
		return useraccountService.findByUserID(userID);
		
	}
}
