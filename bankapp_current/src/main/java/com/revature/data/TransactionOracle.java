package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.beans.Transaction;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class TransactionOracle implements TransactionDAO {
	private Logger log = Logger.getLogger(Transaction.class);
	private ConnectionUtil cu = ConnectionUtil.getInstance();
	@Override
	public int addTransaction(Account acc, double amount, String type) {
		int key = 0;
		log.trace("Adding transaction to database.");
		log.trace(acc);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into transaction (account_id, amount, type) values(?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, acc.getId());
			pstm.setDouble(2, amount);
			pstm.setString(3, type);
			
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("Account created.");
				key = rs.getInt(1);
				conn.commit();
			}
			else
			{
				log.trace("Account not created.");
				conn.rollback();
			}
		}
		catch(Exception e)
		{
			LogUtil.rollback(e,conn,Transaction.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,Transaction.class);
			}
		}
		return key;
	}

	@Override
	public ArrayList<Transaction> getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateTransaction() {
		// TODO Auto-generated method stub
		return 0;
	}

}
