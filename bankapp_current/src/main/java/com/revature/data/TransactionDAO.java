package com.revature.data;

import java.util.ArrayList;

import com.revature.beans.Account;
import com.revature.beans.Transaction;

public interface TransactionDAO {
	public int addTransaction(Account acc, double amount, String type);
	public ArrayList<Transaction> getTransaction();
	public int updateTransaction();
	
	
	
}
