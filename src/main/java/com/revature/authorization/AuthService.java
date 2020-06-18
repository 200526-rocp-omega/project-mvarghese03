

package com.revature.authorization;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.RoleNotAllowedException;
import com.revature.models.User;
import com.revature.services.UserAccountService;

public class AuthService 
{
	private static final UserAccountService useraccountService = new UserAccountService();

	public static void guard(HttpSession session, String...roles) 
	{
		User currentUser = session == null ? null : (User) session.getAttribute("currentUser");
		if(session == null || currentUser == null) {
			throw new NotLoggedInException();
		}
		
		boolean found = false;
		String role = currentUser.getRole().getRole();
		for(String allowedRole : roles) {
			if(allowedRole.equals(role)) {
				found = true;
				break;
			}
		}
		
		if(!found) {
			throw new RoleNotAllowedException();
		}
	}
	
	public static void guard(HttpSession session, int id, String...roles) 
	{
		try {
			guard(session, roles);
		} catch(RoleNotAllowedException e) {
			User current = (User) session.getAttribute("currentUser");
			if(id != current.getId()) {
				throw e;
			}
		}
	}
	
	public static void guard(HttpSession session, int id, int accountid, String...roles) 
	{
		try {
			guard(session, roles);
		} catch(RoleNotAllowedException e) {
			User current = (User) session.getAttribute("currentUser");
			int userid = current.getId();
			int x = useraccountService.checkAccountOwner(userid, accountid);
			if (x == 0) {
				throw e;
			}
		}
	}
}
