package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.models.UsersAccounts;

public interface IUserAccountDAO {
	
	public List<UsersAccounts> findByUserID(int userID);
	public List<UsersAccounts> findByAccountID(int accountID);

}
