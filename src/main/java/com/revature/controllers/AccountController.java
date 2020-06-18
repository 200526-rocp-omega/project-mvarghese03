package com.revature.controllers;

import java.util.List;
import com.revature.models.Account;
import com.revature.services.AccountService;
import com.revature.templates.AccountUserTemplate;
import com.revature.templates.TransferTemplate;
import com.revature.templates.WithdrawTemplate;


public class AccountController {

	private final AccountService accountService = new AccountService();
	
	public List<Account> findAllAccounts() {
		return accountService.findAllAccounts(); // READ
	}
	
	public Account findAccountById(int id) {
		return accountService.findAccountsById(id);
	}
	
	public List<Account> findAccountByStatus(int status ) {
		return accountService.findAccountsByStatus(status);
	}
	
	public List<Account> findAccountByUserID(int userid ) {
		return accountService.findAccountsByUserID(userid);
	}
	
	public int submit(AccountUserTemplate a ) {
		return accountService.submit(a);
	}
	
	public int update(Account a ) {
		return accountService.update(a);
	}
	
	public int withdraw(WithdrawTemplate w)  {
		return accountService.withdraw(w);
	}
	
	public int deposit(WithdrawTemplate w)  {
		return accountService.deposit(w);
	}
	
	public int transferfromSource(TransferTemplate t)  {
		return accountService.transferfromSource(t);
	}
	
	public int transfertoTarget(TransferTemplate t)  {
		return accountService.transfertoTarget(t);
	}
	
	public int insertUsersAccounts(int userid)  {
		return accountService.insertUsersAccounts(userid);
	}
	
	public int acrrueSavingsInterest (double interestrate,int noOfMonths){
		return accountService.acrrueSavingsInterest(interestrate,noOfMonths  );
	}
	
}

	