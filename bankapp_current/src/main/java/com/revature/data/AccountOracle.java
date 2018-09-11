package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class AccountOracle implements AccountDAO {
	private Logger log = Logger.getLogger(AccountOracle.class);
	private ConnectionUtil cu = ConnectionUtil.getInstance();
	
	@Override
	public int addAccount(Account account) {
		int key = 0;
		log.trace("Adding account to database.");
		log.trace(account);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into account (balance, account_type, approved) values(?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setDouble(1, account.getBalance());
			pstm.setString(2, account.getAccount_type());
			pstm.setString(3, account.getApproved());
			
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
			LogUtil.rollback(e,conn,AccountOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,AccountOracle.class);
			}
		}
		return key;
	}

	@Override
	public Account getAccount(Account a) {
		try(Connection conn = cu.getConnection())
		{
			log.trace("retrieving account information");
			String sql = "select account_type, balance, approved from account where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getId());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				log.trace("account found");
				a = new Account();
				a.setId(rs.getInt("id"));
				a.setAccount_type(rs.getString("account_type"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getString("approved"));
			}
		} catch (Exception e) {
			LogUtil.logException(e,AccountOracle.class);
		}
		return a;
	}
	
	public Account getAccount(int i) {
		Account a =null;
		try(Connection conn = cu.getConnection())
		{
			log.trace("retrieving account information");
			String sql = "select id, account_type, balance, approved from account where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				log.trace("account found");
				a = new Account();
				a.setId(rs.getInt("id"));
				a.setAccount_type(rs.getString("account_type"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getString("approved"));
			}
		} catch (Exception e) {
			LogUtil.logException(e,AccountOracle.class);
		}
		return a;
	}

	@Override
	public void deleteAccount(Account account) {
		log.trace("Removing account from database.");
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			String sql = "delete from account where id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, account.getId());
			int number = pstm.executeUpdate();
			if(number!=1)
			{
				log.warn("account not deleted.");
				conn.rollback();
			}
			else
			{
				log.trace("account deleted.");
				conn.commit();
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e,AccountOracle.class);
		}
	}

	@Override
	public void updateAccount(Account account) {
		
	}

	@Override
	public double updateBalance(Account account) {
		log.trace("Updating account from database.");
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			String sql = "update account set balance=? where id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, account.getBalance());
			pstm.setInt(2, account.getId());
			int number = pstm.executeUpdate();
			if(number!=1)
			{
				log.warn("account not updated.");
				conn.rollback();
			}
			else
			{
				log.trace("account updated.");
				conn.commit();
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e,AccountOracle.class);
		}
		return 0;
	}

	@Override
	public void approveAccount(int i) {
		log.trace("Approving Account");
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			String sql = "update account set approved='true' where id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1,i);
			int number = pstm.executeUpdate();
			if(number!=1)
			{
				log.warn("account not updated.");
				conn.rollback();
			}
			else
			{
				log.trace("account updated.");
				conn.commit();
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e,AccountOracle.class);
		}

	}

	@Override
	public ArrayList<Account> getAccounts() {
		List<Account> aList = new ArrayList<Account>();
		Account a = null;
		try(Connection conn = cu.getConnection())
		{
			log.trace("retrieving all account");
			String sql = "select * from account";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				log.trace("account found");
				a = new Account();
				a.setId(rs.getInt("id"));
				a.setAccount_type(rs.getString("account_type"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getString("approved"));
				aList.add(a);
			}
		} catch (Exception e) {
			LogUtil.logException(e,AccountOracle.class);
		}
		return (ArrayList<Account>) aList;
	}

	@Override
	public ArrayList<Account> getUnapprovedAccounts() {
		List<Account> aList = new ArrayList<Account>();
		Account a = null;
		try(Connection conn = cu.getConnection())
		{
			log.trace("retrieving all account");
			String sql = "select * from account where approved = 'false'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				log.trace("account found");
				a = new Account();
				a.setId(rs.getInt("id"));
				a.setAccount_type(rs.getString("account_type"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getString("approved"));
				aList.add(a);
			}
		} catch (Exception e) {
			LogUtil.logException(e,AccountOracle.class);
		}
		return (ArrayList<Account>) aList;
	}


}
