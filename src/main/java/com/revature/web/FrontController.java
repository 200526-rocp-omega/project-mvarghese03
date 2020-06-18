package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.authorization.AuthService;
import com.revature.controllers.AccountController;
import com.revature.controllers.UserController;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.RoleNotAllowedException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.models.UsersAccounts;
import com.revature.services.AccountService;
import com.revature.services.UserAccountService;
import com.revature.services.UserService;
import com.revature.templates.AccountUserTemplate;
import com.revature.templates.InterestAccrualTemplate;
import com.revature.templates.LoginTemplate;
import com.revature.templates.MessageTemplate;
import com.revature.templates.TransferTemplate;
import com.revature.templates.WithdrawTemplate;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = -4854248294011883310L;
	private static final UserController userController = new UserController();
	private static final AccountController accountController = new AccountController();
	private static final ObjectMapper om = new ObjectMapper();
	private static final UserService service = new UserService();
	private static final AccountService accountService = new AccountService();
	private static final UserAccountService useraccountService = new UserAccountService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		res.setStatus(404);
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");

		String[] portions = URI.split("/");
		

//		System.out.println(Arrays.toString(portions));

		try {
			switch (portions[0]) {
			case "users":
				if (portions.length == 2) {
					// Delegate to a Controller method to handle obtaining a User by ID
					int id = Integer.parseInt(portions[1]);
					AuthService.guard(req.getSession(false), id, "Employee", "Admin");
					User u = userController.findUserById(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(u));
				} else {
					// Delegate to a Controller method to handle obtaining ALL Users
					AuthService.guard(req.getSession(false),"Employee", "Admin");
					List<User> all = userController.findAllUsers();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
				}
				break;
			case "accounts":

				if ((portions.length > 1) && (portions[1].matches("status"))) 
				{
					int statusid = Integer.parseInt(portions[2]);
					AuthService.guard(req.getSession(false),  "Employee", "Admin");
					List<Account> accountsByStatus = accountController.findAccountByStatus(statusid);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(accountsByStatus));
				
				} else if ((portions.length > 1) && (portions[1].matches("owner"))) {
					int userid = Integer.parseInt(portions[2]);
					AuthService.guard(req.getSession(false), userid, "Employee", "Admin");
					List<Account> accountsByUserID = accountController.findAccountByUserID(userid);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(accountsByUserID));

				}

				else if (portions.length == 2) 
				{

					int accountid = Integer.parseInt(portions[1]);
					
					User current = (User) req.getSession().getAttribute("currentUser");
					int userid= current.getId();
					
					AuthService.guard(req.getSession(false),userid,accountid, "Employee", "Admin");
					
					
					Account a = accountController.findAccountById(accountid);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(a));}
					
				 else  {

					AuthService.guard(req.getSession(false), "Employee", "Admin");
					System.out.println("test");
					List<Account> all = accountController.findAllAccounts();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
				}

				break;
			}
		} catch (NotLoggedInException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("Please log in to access records!");
			res.getWriter().println(om.writeValueAsString(message));
		} catch (RoleNotAllowedException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("You do not have the privileges to perform this function!");
			res.getWriter().println(om.writeValueAsString(message));
		} catch (AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		res.setStatus(404);
		
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");

		String[] portions = URI.split("/");

		try 
		{
			switch (portions[0]) {
			case "logout":
				if (userController.logout(req.getSession(false))) {
					res.setStatus(200);
					res.getWriter().println("You have been successfully logged out");
				} else {
					res.setStatus(400);
					res.getWriter().println("You were not logged in to begin with");
				}
				break;
			case "accounts":
				if ((portions.length == 2) && (portions[1].matches("withdraw"))) {
					User current = (User) req.getSession().getAttribute("currentUser");
					int userid= current.getId();
					BufferedReader reader = req.getReader();
					StringBuilder sb = new StringBuilder();
					String line;
					 while( (line = reader.readLine()) != null ) { 							 
						  sb.append(line); } 
					  
					  String body = sb.toString(); 
					  WithdrawTemplate withdrawfromAccount = om.readValue(body, WithdrawTemplate.class);
					  double amount = withdrawfromAccount.getAmount();
					  int accountID = withdrawfromAccount.getAccountId();
					  AuthService.guard(req.getSession(false),userid,accountID,  "Admin");
					  int i = accountService.CheckwithdrawAmount( accountID,  amount);
					  if (i==0) {
						  res.setStatus(400);
							res.getWriter().println("Transaction cannot be completed as account balance will go negative!");
					  } else {
						  	
						  	int x = accountController.withdraw(withdrawfromAccount);
						  	res.setStatus(200);
							res.getWriter().println("$" + amount + " has been withdrawn from Account #" + accountID);
						  }
					
				} else if 
						((portions.length == 2) && (portions[1].matches("deposit"))) {
						User current = (User) req.getSession().getAttribute("currentUser");
						int userid= current.getId();
						BufferedReader reader = req.getReader();
						StringBuilder sb = new StringBuilder();
						String line;
						 while( (line = reader.readLine()) != null ) { 							 
							  sb.append(line); } 
						  
						  String body = sb.toString(); 
						  WithdrawTemplate withdrawfromAccount = om.readValue(body, WithdrawTemplate.class);
						  double amount = withdrawfromAccount.getAmount();
						  int accountID = withdrawfromAccount.getAccountId();
						  AuthService.guard(req.getSession(false),userid,accountID,  "Admin");
						 			int x = accountController.deposit(withdrawfromAccount);
							  	res.setStatus(200);
								res.getWriter().println("$" + amount + " has been deposited to Account #" + accountID);
							  
				} else if 
						((portions.length == 2) && (portions[1].matches("transfer"))) 
				{
						User current = (User) req.getSession().getAttribute("currentUser");
						int userid= current.getId();
						BufferedReader reader = req.getReader();
						StringBuilder sb = new StringBuilder();
						String line;
						 while( (line = reader.readLine()) != null ) { 							 
							  sb.append(line); } 
						  
						  String body = sb.toString(); 
						 TransferTemplate transferamount = om.readValue(body,TransferTemplate.class);
						  double amount = transferamount.getAmount();
						  int sourceAccountID = transferamount.getSourceAccountId();
						  int targetAccountID = transferamount.getTargetAccountId();
						  AuthService.guard(req.getSession(false),userid,sourceAccountID,  "Admin");
						  int i = accountService.CheckwithdrawAmount( sourceAccountID,  amount);
						  if (i==0) {
							  res.setStatus(400);
								res.getWriter().println("Transaction cannot be completed as account balance will go negative!");
						  } else {
							  int x = accountController.transferfromSource(transferamount);
							  	if (x==1) 
							  	{
									int y = accountController.transfertoTarget(transferamount);
									res.setStatus(200);
									res.getWriter().println("$" + amount + " has been transferred from Account # " + sourceAccountID + " to Account # " + targetAccountID);
							  }
						  }
				}
				
				else {
					User current = (User) req.getSession().getAttribute("currentUser");
					int iuserid= current.getId();
					
				AuthService.guard(req.getSession(false), iuserid, "Admin"); 
				BufferedReader reader = req.getReader();
				StringBuilder sb = new StringBuilder();
				String line;
				 while( (line = reader.readLine()) != null ) { 							 
					  sb.append(line); } 
				  
				  String body = sb.toString(); 
				 
				  AccountUserTemplate addAccount = om.readValue(body, AccountUserTemplate.class);
				  int y = accountController.submit(addAccount);
				  	if ( y==1) {
				  		
					  int x = accountController.insertUsersAccounts(iuserid);
					  res.setStatus(201);
					  res.getWriter().println("Account has been successfully created!");
				  } }
				break;
			case "passTime":
				AuthService.guard(req.getSession(false), "Admin");
				String accountType = "Savings";
				BufferedReader reader = req.getReader();
				StringBuilder sb = new StringBuilder();
				String line;
				
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				String body = sb.toString();
				InterestAccrualTemplate interest = om.readValue(body, InterestAccrualTemplate.class);
				double interestrate = 0.02;
				int noOfMonths = interest.getNumOfMonths();
				int x = accountController.acrrueSavingsInterest(interestrate,noOfMonths);
				 res.setStatus(201);
				  res.getWriter().println(noOfMonths + " months of interest has been accrued for all Savings Accounts!");
			}
		} catch (NotLoggedInException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("Please log in to access records!");
			res.getWriter().println(om.writeValueAsString(message));
		} catch (RoleNotAllowedException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("You do not have the privileges to perform this function!");
			res.getWriter().println(om.writeValueAsString(message));
		} catch (AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}
	

	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		System.out.println("test");
		res.setContentType("application/json");
		res.setStatus(404);
		HttpSession session =req.getSession();
		  
		final String URI = req.getRequestURI().replace("/rocp-project","").replaceFirst("/", ""); String[] portions = URI.split("/");
		System.out.println("test2");
		
		try 
		  {
			  
			  switch(portions[0]) 
			  { 		  
				  case "users": 
					
					if(portions.length>0) 
					{ 	BufferedReader reader = req.getReader();
						  
						  StringBuilder sb = new StringBuilder();
						  
						  String line;
						  
						  while( (line = reader.readLine()) != null ) { 							 
							  sb.append(line); } 
						  
						  String body = sb.toString(); 
						 
						  User userToUpdate = om.readValue(body, User.class);
						  
						  // Verify that user is an admin
						  int id = userToUpdate.getId(); 
						  AuthService.guard(req.getSession(false), id, "Admin");
						  
						  int i = userController.update(userToUpdate);
						 
						  res.setStatus(200);
						  res.getWriter().println("User record has been successfully updated!");
						  System.out.println("test8");
					} 
			  break;
			  
				  case "accounts": 
					  
					  AuthService.guard(req.getSession(false), "Admin");
					  
						if(portions.length>0) 
						{ 	  BufferedReader reader = req.getReader();
							  
							  StringBuilder sb = new StringBuilder();
							  
							  String line;
							  
							  while( (line = reader.readLine()) != null ) { 							 
								  sb.append(line); } 
							  
							  String body = sb.toString(); 
							 
							  Account accountToUpdate = om.readValue(body, Account.class);
							  
							  int i = accountController.update(accountToUpdate);
							 
							  res.setStatus(200);
							  res.getWriter().println("Account has been successfully updated!");
							  System.out.println("test9");
						} 
				  break; 
			  }
		  
		  }catch (NotLoggedInException e) 
		  { res.setStatus(401); MessageTemplate message = new MessageTemplate("Please log in to access records!");
		    res.getWriter().println(om.writeValueAsString(message)); 
		  } catch (RoleNotAllowedException e) 
		  { res.setStatus(401); 
		     MessageTemplate message = new MessageTemplate("You do not have the privileges to perform this function!");
		     res.getWriter().println(om.writeValueAsString(message)); 
		  } catch(AuthorizationException e) 
		  {  res.setStatus(401); 
		     MessageTemplate message = new MessageTemplate("The incoming token has expired");
		     res.getWriter().println(om.writeValueAsString(message)); }

	}

}
