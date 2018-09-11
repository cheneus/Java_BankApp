package com.revature.services;

import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.beans.Login;
import com.revature.data.BankAppDAOFactory;
import com.revature.data.CustomerDAO;
import com.revature.data.LoginDAO;

public class LoginServiceOracle implements LoginService {
	private Logger log = Logger.getLogger(LoginServiceOracle.class);
	private BankAppDAOFactory bf = BankAppDAOFactory.getInstance();
	private LoginDAO ad = bf.getLoginDAO();
	private CustomerDAO cd = bf.getCustomerDAO();
	
	@Override
	public Set<Login> getLogins() {
		Set<Login> logList = ad.getLogins();
		for(Login l : logList) {
//			c.setAddress(addrdao.getAddress(c.getAddress().getId()));
			

		}
		return logList;
	}

	@Override
	public void updateLogin(Login a) {
		ad.updateLogin(a);
	}

	@Override
	public void deleteLogin(Login a) {
		ad.deleteLogin(a);
	}

	@Override
	public void addLogin(Login a) {
		ad.addLogin(a);
	}

	@Override
	public Login getLogin(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
