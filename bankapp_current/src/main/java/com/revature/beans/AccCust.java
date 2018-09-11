package com.revature.beans;

public class AccCust {
	private int id;
	private Account account_id;
	private Customer customer_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Account getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Account account_id) {
		this.account_id = account_id;
	}
	public Customer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Customer customer_id) {
		this.customer_id = customer_id;
	}
}
