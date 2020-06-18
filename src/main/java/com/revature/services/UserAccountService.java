package com.revature.services;

import java.util.List;

import com.revature.dao.IUserAccountDAO;
import com.revature.dao.IUserDAO;
import com.revature.dao.UserAccountDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.RoleNotAllowedException;
import com.revature.models.UsersAccounts;

public class UserAccountService implements IUserAccountDAO
{
	private IUserAccountDAO dao = new UserAccountDAO();
	
	@Override
	public List<UsersAccounts> findByUserID(int userID) {
		return dao.findByUserID(userID);
		
	}

	@Override
	public List<UsersAccounts> findByAccountID(int accountID) {
		return dao.findByAccountID(accountID);
	}
	
	public int checkAccountOwner(int myuserid, int accountid)  
	{
		
		List<UsersAccounts> userAccount = findByAccountID(accountid);
		for (int i = 0; i < userAccount.size();i++)
		{UsersAccounts ua = userAccount.get(i);
		int userid = ua.getUserId();
		if (myuserid == userid) 
		{
					return 1;
		}
		}
		return 0;
		 
		}
}

	
	


