package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.AccCust;
import com.revature.beans.Account;
import com.revature.beans.Customer;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class AccCustOracle implements AccCustDAO {
	private Logger log = Logger.getLogger(AccCustOracle.class);
	private ConnectionUtil cu = ConnectionUtil.getInstance();

	@Override
	public int addAccCust(Account account, Customer customer) {
		int key = 0;
		log.trace("Adding account_customer to database.");
		log.trace(account);
		log.trace(customer);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into account_customer (account_id, customer_id) values(?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, account.getId());
			pstm.setInt(2, customer.getId());
			
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
			LogUtil.rollback(e,conn,AccCustOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,AccCustOracle.class);
			}
		}
		return key;
	}

	@Override
	public AccCust getAccCust(Account account) {
		AccCust acc_cust = null;
//		try(Connection conn = cu.getConnection())
//		{
//			log.trace("retrieving account information");
//			String sql = "select accounttype, balance, approved from account where id=?";
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setInt(1, a.getId());
//			ResultSet rs = ps.executeQuery();
//			if(rs.next())
//			{
//				log.trace("account found");
//				a = new Account();
//				a.setId(rs.getInt("id"));
//				a.setAccount_type(rs.getString("accounttype"));
//				a.setBalance(rs.getDouble("balance"));
//				a.setApproved(rs.getString("approved"));
//			}
//		} catch (Exception e) {
//			LogUtil.logException(e,AccountOracle.class);
//		}
		return acc_cust;
	}

	@Override
	public List<AccCust> AccCustByCustId(Customer cust) {
		List<AccCust> acc_cust_list = new ArrayList<AccCust>();

		Customer c = cust;
		try(Connection conn = cu.getConnection())
		{
			log.trace("retrieving account information");
			String sql = "select account_customer.id, account.balance, account.account_type, account_customer.account_id, account.approved from (account join account_customer on account.id = account_customer.account_id) where account_customer.customer_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cust.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				log.trace("account found");
				AccCust acc_cust = new AccCust();
				Account a = new Account();
				a.setBalance(rs.getDouble(2));
				a.setAccount_type(rs.getString(3));
				a.setId(rs.getInt(4));
				a.setApproved(rs.getString(5));
				
				acc_cust.setId(rs.getInt(1));
				acc_cust.setAccount_id(a);
				acc_cust.setCustomer_id(c);
				acc_cust_list.add(acc_cust);
			}
		} catch (Exception e) {
			LogUtil.logException(e,AccCustOracle.class);
		}
		return acc_cust_list;
	}

	@Override
	public void deleteAccCust(Account account) {
		// TODO Auto-generated method stub

	}

}
