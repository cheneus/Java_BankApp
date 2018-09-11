package com.revature.data;

import java.util.List;

import com.revature.beans.AccCust;
import com.revature.beans.Account;
import com.revature.beans.Customer;

public interface AccCustDAO {
public int addAccCust(Account account, Customer customer);
	
	/**
	 * returns a login object from the database
	 * 
	 * @param id the id of the User
	 * @return the Account from the database that matches the id,
	 * null if no Account with said id exists.
	 */
	public AccCust getAccCust(Account account);
	public List<AccCust> AccCustByCustId (Customer cust);
	
	/**
	 * deletes a Account from the database
	 * 
	 * @param Account the Account to be deleted
	 */
	public void deleteAccCust(Account account);
	
	/**
	 * updates a Account in the database
	 * 
	 * @param Account the Account to be updated
	 */
}
