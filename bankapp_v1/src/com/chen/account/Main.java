package com.chen.account;

import java.io.Serializable;
import java.util.Date;

public class Main implements Serializable {
	private static final long serialVersionUID = -8240835298230143497L;

	private String username;
	private String password;
	private boolean jointed;
	private double balance;
	private String fname;
	private String lname;
	private String phone;
	private String dob;
	private boolean approved;

	public Main(String username, String password, boolean jointed, String fname, String lname, String phone,String dob, double balance, boolean approved) {
		super();
		this.username = username;
		this.password = password;
		this.jointed = jointed;
		this.balance = balance;
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
		this.dob = dob;
		this.approved = approved;
	}
	
	public String getInfo() {
		System.out.println("Name:"+ fname+ "," +lname +"\n" + "jointed:" +jointed + "\nbalance: " + balance) ;
		
		return ("approved: "+approved);
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isJointed() {
		return jointed;
	}

	public void setJointed(boolean jointed) {
		this.jointed = jointed;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
		System.out.println(getBalance());
	}
	
	public void withdraw (double funds) {
		balance = getBalance();
		double newBalance = balance - funds;
		if (newBalance < 0) {
			System.out.println("Insufficient Funds");
		}
		else {
			setBalance(newBalance);
		}
	}
	
	public void deposit (double funds) {
		balance = getBalance();
		double newBalance = balance + funds;
			setBalance(newBalance);
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDob() {
		return dob;
	}
	
	public void setDob(String dob) {
		this.dob=dob;
		
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
}
