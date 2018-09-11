package com.revature.beans;

import java.util.ArrayList;

public class Admin extends Customer {
	
	private ArrayList<Account> Accounts;
	private ArrayList<Account> UnapprovedAccounts;
	private ArrayList<Customer> Customers;
	private ArrayList<Login> Logins;
	private ArrayList<Object> Acc_Cust;
	
	public ArrayList<Account> getAccounts() {
		return Accounts;
	}
	public void setAccounts(ArrayList<Account> accounts) {
		Accounts = accounts;
	}
	public ArrayList<Customer> getCustomers() {
		return Customers;
	}
	public void setCustomers(ArrayList<Customer> customers) {
		Customers = customers;
	}
	public ArrayList<Login> getLogins() {
		return Logins;
	}
	public void setLogins(ArrayList<Login> logins) {
		Logins = logins;
	}
	public ArrayList<Object> getAcc_Cust() {
		return Acc_Cust;
	}
	public void setAcc_Cust(ArrayList<Object> acc_Cust) {
		Acc_Cust = acc_Cust;
	}
	public ArrayList<Account> getUnapprovedAccounts() {
		return UnapprovedAccounts;
	}
	public void setUnapprovedAccounts(ArrayList<Account> unapprovedAccounts) {
		UnapprovedAccounts = unapprovedAccounts;
	}
	
	
}
