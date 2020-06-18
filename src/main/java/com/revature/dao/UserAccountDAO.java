package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.models.UsersAccounts;
import com.revature.util.ConnectionUtil;

public class UserAccountDAO implements IUserAccountDAO {

	@Override
	public List<UsersAccounts> findByUserID(int userID) 
	{
		List<UsersAccounts> myUserAccounts = new ArrayList<>();
		
			try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS_ACCOUNTS WHERE user_id = ? ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,  userID);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				int userid  = rs.getInt("user_id");
				int accountid = rs.getInt("account_id");
				
				UsersAccounts ua = new UsersAccounts(userid,accountid);
				
				myUserAccounts.add(ua);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		 
		return myUserAccounts;
	}


	@Override
	public List<UsersAccounts> findByAccountID(int accountID) {
		
		List<UsersAccounts> myUserAccounts = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
		String sql = "SELECT * FROM USERS_ACCOUNTS WHERE account_id = ? ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,  accountID);
		
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) 
		{
			int userid  = rs.getInt("user_id");
			int accountid = rs.getInt("account_id");
			
			UsersAccounts ua = new UsersAccounts(userid,accountid);
			
			myUserAccounts.add(ua);
		}
		
	} catch(SQLException e) {
		e.printStackTrace();
		return new ArrayList<>();
	}
	 
	return myUserAccounts;
	}

}
