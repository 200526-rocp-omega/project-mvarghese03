package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.templates.AccountUserTemplate;
import com.revature.templates.TransferTemplate;
import com.revature.templates.WithdrawTemplate;
import com.revature.util.ConnectionUtil;

public class AccountDAO implements IAccountDAO {
	
	@Override
	public List<Account> findAll() {
		
		List<Account> allAccounts = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS "
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id"
					+ " INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id "
					+ "ORDER BY ACCOUNTS. id";
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				// We obtain the data from every column that we need
				
				int ID = rs.getInt("id");
				double balance  = rs.getDouble("balance");
				int statusID = rs.getInt("status_id");
				String statusName = rs.getString("status");
				int typeID = rs.getInt("type_id");
				String typeName = rs.getString("type");
			
				
				// And use that data to create a User object accordingly
				AccountStatus accountStatus = new AccountStatus(statusID, statusName);
				AccountType accountType = new AccountType(typeID, typeName);
				
				
				Account a = new Account(ID, balance, accountStatus,accountType );
				
				// Then we make sure to add this User to our list
				allAccounts.add(a);
			}
		} catch(SQLException e) {
			// If something goes wrong, return an empty list
			e.printStackTrace();
			return new ArrayList<>();
		}
		
		// At the end we simply return all of our Users
		return allAccounts;
	
		}

	@Override
	public int submit(AccountUserTemplate a) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			String columns = "balance, status_id, type_id";
			
			String sql = "INSERT INTO ACCOUNTS (" + columns + 
					") VALUES (?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, a.getBalance());
			stmt.setInt(2, a.getStatus().getStatusId()); 
			stmt.setInt(3,a.getType().getTypeId());
			
			return stmt.executeUpdate();
			
		} catch(SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}
	

	public int insertUsersAccounts(int userid) 
	{
		try(Connection conn = ConnectionUtil.getConnection()) 
		
		{
			String columns = "user_id, account_id";
			String sql = "INSERT INTO USERS_ACCOUNTS (" + columns + ") VALUES (?, (SELECT max(id) FROM ACCOUNTS))";
					
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			
			return stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		 
		return 0;
	}
	@Override
	public Account findById(int id) 
	{
		try(Connection conn = ConnectionUtil.getConnection()) 
		{
			String sql = "SELECT * FROM ACCOUNTS "
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id"
					+ " INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id  WHERE ACCOUNTS.id = ?";
					
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,  id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				double balance  = rs.getDouble("balance");
				int statusID = rs.getInt("status_id");
				String statusName = rs.getString("status");
				int typeID = rs.getInt("type_id");
				String typeName = rs.getString("type");	
				AccountStatus accountStatus = new AccountStatus(statusID, statusName);
				AccountType accountType = new AccountType(typeID, typeName);
				
				return new Account(id , balance, accountStatus,accountType );
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		 
		return null;
	}

	@Override
	public List<Account> findByUserID(int userID) {
		List<Account> accountsByUserID = new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection()) 
		{
			String sql = "SELECT ACCOUNTS.id as id, balance, status_id, status, type_id, type FROM USERS_ACCOUNTS INNER JOIN ACCOUNTS ON USERS_ACCOUNTS.account_id = ACCOUNTS. id "
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id"
					+ " INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id "
					+ " INNER JOIN USERS ON USERS_ACCOUNTS.user_id = USERS.id WHERE USERS_ACCOUNTS.user_id = ?";
					
					
			PreparedStatement stmt = conn.prepareStatement(sql);
			System.out.println("testaccount");
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
		
			while(rs.next()) 
			{
				int id = rs.getInt("id");
				double balance  = rs.getDouble("balance");
				int statusID = rs.getInt("status_id");
				String statusName = rs.getString("status");
				int typeID = rs.getInt("type_id");
				String typeName = rs.getString("type");	
				AccountStatus accountStatus = new AccountStatus(statusID, statusName);
				AccountType accountType = new AccountType(typeID, typeName);
				
				Account a = new Account(id, balance, accountStatus,accountType );
				accountsByUserID.add(a);
				
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		 
		return accountsByUserID;


	
	}

	@Override
	public List<Account> findByStatus(int statusid) {
		List<Account> accountsByStatus = new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection()) 
		{
			String sql = "SELECT * FROM ACCOUNTS "
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id"
					+ " INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id  WHERE ACCOUNT_STATUS.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			System.out.println("test account" + sql);
			stmt.setInt(1, statusid);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				int id = rs.getInt("id");
				double balance  = rs.getDouble("balance");
				int statusID = rs.getInt("status_id");
				String statusName = rs.getString("status");
				int typeID = rs.getInt("type_id");
				String typeName = rs.getString("type");	
				AccountStatus accountStatus = new AccountStatus(statusID, statusName);
				AccountType accountType = new AccountType(typeID, typeName);
				
				Account a = new Account(id, balance, accountStatus,accountType );
				accountsByStatus.add(a);
				
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		 
		return accountsByStatus;

	}

	@Override
	public int update(Account a) {
try (Connection conn= ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE ACCOUNTS SET balance = ? , status_id = ? , type_id = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, a.getBalance());
			stmt.setInt(2, a.getStatus().getStatusId());
			stmt.setInt(3, a.getType().getTypeId());
			stmt.setInt(4,a.getAccountId());
			
			System.out.println(sql);
			return stmt.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
	return 0;	
	}

	@Override
	public int withdraw(WithdrawTemplate w) 
	{
			try (Connection conn= ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE ACCOUNTS SET balance = (balance - ?)  where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, w.getAmount());
			int ID =w.getAccountId();
			stmt.setInt(2, ID);
			
			
			System.out.println(sql);
			return stmt.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
	return 0;	
	}
	
	@Override
	public int deposit(WithdrawTemplate w) 
	{
			try (Connection conn= ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE ACCOUNTS SET balance = (balance + ?)  where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, w.getAmount());
			int ID =w.getAccountId();
			stmt.setInt(2, ID);
			
			
			System.out.println(sql);
			return stmt.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
	return 0;	
	}
	
	@Override
	public int transferfromSource(TransferTemplate t) 
	{
			try (Connection conn= ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE ACCOUNTS SET balance = (balance - ?)  where id = ?" ;
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1,t.getAmount());
			stmt.setInt(2,t.getSourceAccountId());
			
			System.out.println(sql);
			return stmt.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
	return 0;	
	}
	
	@Override
	public int transfertoTarget(TransferTemplate t) 
	{
			try (Connection conn= ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE ACCOUNTS SET balance = (balance + ?)  where id = ?" ;
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1,t.getAmount());
			stmt.setInt(2, t.getTargetAccountId());
			
			System.out.println(sql);
			return stmt.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
	return 0;	
	}
	
	@Override
	public int acrrueSavingsInterest(double interestrate, int noOfMonths)
	{
			try (Connection conn= ConnectionUtil.getConnection()) {
				double intIncrement = ((0.02/365)*30*noOfMonths);
			
			String sql = "UPDATE ACCOUNTS SET balance = (balance + (?*balance))  where type_id = 2" ;
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1,intIncrement);
			System.out.println(sql);
			return stmt.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
	return 0;	
	}
	
	}
	
	
