package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.templates.AccountUserTemplate;
import com.revature.templates.TransferTemplate;
import com.revature.templates.WithdrawTemplate;


/**
 * This is an interface that defines what methods we plan to use to
 * interact with the database.
 * 
 * We have methods for all 4 CRUD operations.
 * 
 * We additionally have a few extra helpful Read operations,
 * since they might come in handy.
 * 
 * It is good design to have an interface for our DAO classes.
 */
public interface IAccountDAO {

	public int submit(AccountUserTemplate a); // CREATE
	public List<Account> findAll(); // READ
	public Account findById(int id);
	public List<Account> findByUserID(int userID);
	public List<Account> findByStatus(int statusid);
	public int update(Account a); // UPDATE
	public int insertUsersAccounts(int userid) ;
	public int withdraw(WithdrawTemplate withdrawfromAccount); // withdraw
	public int deposit(WithdrawTemplate withdrawfromAccount); // withdraw
	public int transferfromSource(TransferTemplate t);
	public int transfertoTarget(TransferTemplate t);
	public int acrrueSavingsInterest(double interestrate, int noOfMonths);
//	public String transfer()
}