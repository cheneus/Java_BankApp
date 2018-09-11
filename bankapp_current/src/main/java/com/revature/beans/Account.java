package com.revature.beans;

import java.io.Serializable;

public class Account implements Serializable {
	private static final long serialVersionUID = 2806197599637512241L;
	
	private int id;
	private Double balance;
	private String approved;
	private String account_type;
	
	public Account() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	
	public double deposit(double i) {
		balance = getBalance();
		double new_balance =balance + i;
		setBalance(new_balance);
		return new_balance;
	}
	
	public double withdraw(double i) {
		balance = getBalance();
		double new_balance =balance - i;
		if (new_balance < 0) {
			System.out.println("Insufficient Funds");
			System.out.println("You can only withdraw " + balance);
			return balance;
		} else {
			setBalance(new_balance);
		}
		return new_balance;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((approved == null) ? 0 : approved.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((account_type == null) ? 0 : account_type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (approved == null) {
			if (other.approved != null)
				return false;
		} else if (!approved.equals(other.approved))
			return false;
		if (id != other.id)
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (account_type == null) {
			if (other.account_type != null)
				return false;
		} else if (!account_type.equals(other.account_type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Account [accountId=" + id + ", balance=" + balance + ", type=" + account_type + ", approved=" + approved
				+ "]";
	}
}