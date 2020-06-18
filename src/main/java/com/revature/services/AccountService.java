package com.revature.services;

import java.util.List;
import com.revature.dao.AccountDAO;
import com.revature.dao.IAccountDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.models.UsersAccounts;
import com.revature.templates.AccountUserTemplate;
import com.revature.templates.TransferTemplate;
import com.revature.templates.WithdrawTemplate;


// The service layer is a layer that is designed to enforce your "business logic"
// These are miscellaneous rules that define how your application will function
// 		Ex: May not withdraw money over the current balance
// All interaction with the DAO will be through this service layer
// This design is simply furthering the same design structure that we have used up to now
// How you go about designing the details of this layer is up to you
// Due to the nature of the "business logic" being rather arbitrary
// This layer has the MOST creativity involved
// Most other layers are pretty boilerplate, where you pretty much copy/paste most methods
public class AccountService 
{
	
	private IAccountDAO dao = new AccountDAO();

	// A starting place that I like to use, is to also create CRUD methods in the service layer
	// that will be used to interact with the DAO
	
	// Then additionally, you can have extra methods to enforce whatever features/rules that you want
	// For example, we might also have a login/logout method
	
	public int submit(AccountUserTemplate a) {
		return dao.submit(a);// CREATE
	}
	
	public List<Account> findAllAccounts() {
		return dao.findAll(); // READ
	}
	public Account findAccountsById(int id) {
		return dao.findById(id);
	}
	public List<Account> findAccountsByUserID(int userid) {
		return dao.findByUserID(userid);
	}
	public List<Account> findAccountsByStatus(int statusid) {
		return dao.findByStatus(statusid);
	}
	
	public int update(Account a) {
		return dao.update(a);// UPDATE
	}
	
	public int insertUsersAccounts(int userid)  {
		return dao.insertUsersAccounts(userid);
	}
	
	public int withdraw(WithdrawTemplate w)   {
		return dao.withdraw(w);
	}
	
	public int deposit(WithdrawTemplate w)   {
		return dao.deposit(w);
	}
	
	public int transferfromSource(TransferTemplate t)  {
		return dao.transferfromSource(t);
	}
	
	public int transfertoTarget(TransferTemplate t)  {
		return dao.transfertoTarget(t);
	}
	
	
	public int CheckwithdrawAmount(int accountID, double withdrawAmount)  {
		
		Account a = findAccountsById(accountID);
		double balance = a.getBalance();
		if (balance< withdrawAmount) {
			return 0;
		} else {
		return 1;
			}
		}
	
	public int checktransfer(TransferTemplate transferAmount)  
	{
		int sourceAccountId = transferAmount.getSourceAccountId();
		int targetAccountId = transferAmount.getTargetAccountId();
		double Amount = transferAmount.getAmount();
		
		Account sourceAccount = findAccountsById(sourceAccountId);
		double balance = sourceAccount.getBalance();
		if (balance < Amount) {
			return 0;
		} else {
		return 1;
			}
		
}
	


	public int acrrueSavingsInterest (double interestrate,int noOfMonths)  {
		return dao.acrrueSavingsInterest(interestrate,noOfMonths);
	}
}
	

