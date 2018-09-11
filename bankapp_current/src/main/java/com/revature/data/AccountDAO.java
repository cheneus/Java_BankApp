package com.revature.data;

import java.util.ArrayList;

import com.revature.beans.Account;

public interface AccountDAO {
	/**
	 * Inserts a Account into the database.
	 * 
	 * @param Account the Account object to be inserted
	 * @return id of created Account
	 */
	public int addAccount(Account account);
	
	/**
	 * returns a login object from the database
	 * 
	 * @param id the id of the User
	 * @return the Account from the database that matches the id,
	 * null if no Account with said id exists.
	 */
	public Account getAccount(Account account);
	public ArrayList<Account> getAccounts();
	public ArrayList<Account> getUnapprovedAccounts();
	
	/**
	 * deletes a Account from the database
	 * 
	 * @param Account the Account to be deleted
	 */
	public void deleteAccount(Account account);
	
	/**
	 * updates a Account in the database
	 * 
	 * @param Account the Account to be updated
	 */
	public void updateAccount(Account account);
	public double updateBalance(Account account);
	public void approveAccount(int i);
}
