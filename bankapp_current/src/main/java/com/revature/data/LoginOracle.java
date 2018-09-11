package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.beans.Address;
import com.revature.beans.Customer;
import com.revature.beans.Login;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class LoginOracle implements LoginDAO {
	private static Logger log = Logger.getLogger(LoginOracle.class);
	private static ConnectionUtil cu = ConnectionUtil.getInstance();
	
	@Override
	public int addLogin(Login login) {
		int key =0;
		log.trace("Adding login to database.");
		log.trace(login);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into login (username, password, customer_id, login_type) values(?,?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setString(1,login.getUsername());
			pstm.setString(2, login.getPassword());
			pstm.setInt(3, login.getCust_id().getId());
			pstm.setInt(4, 3);
			
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("Login created.");
				key = rs.getInt(1);
				login.setId(key);
				conn.commit();
			}
			else
			{
				log.trace("User not created.");
				conn.rollback();
			}
		}
		catch(Exception e)
		{
			LogUtil.rollback(e,conn, LoginOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, LoginOracle.class);
			}
		}
		return key;
	}

	@Override
	public Login getLogin(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Login> getLogins() {
		// TODO Auto-generated method stub
		Set<Login> logList = new HashSet<Login>();
		try(Connection conn = cu.getConnection())
		{
			String sql = "Select id,username, password, customer_id, login_type from login";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while(rs.next())
			{
				Login log = new Login();
				log.setCust_id(new Customer(rs.getInt("id")));
				log.setPassword(rs.getString("password"));
				log.setUsername(rs.getString("username"));
				log.setLogin_type(rs.getString("login_type"));
				log.setId(rs.getInt("id"));
				logList.add(log);
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e,CustomerOracle.class);
		}
		return logList;
	}

	@Override
	public Login getLoginById(Login l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLogin(Login login) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLogin(Login login) {
		// TODO Auto-generated method stub

	}

}
