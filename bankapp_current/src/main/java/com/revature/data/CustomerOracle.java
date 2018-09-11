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
import com.revature.exceptions.NullArgumentException;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class CustomerOracle implements CustomerDAO
{
	private Logger log = Logger.getLogger(CustomerOracle.class);
	private ConnectionUtil cu = ConnectionUtil.getInstance();
	
	@Override
	public int addCustomer(Customer customer) {
		int key = 0;
		Connection conn = cu.getConnection();
		try {
			log.trace("Inserting customer into db");
			conn.setAutoCommit(false);
			String[] keys = {"id"};
			String sql = "insert into customer (firstname, lastname, phone, email, address) values (?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql, keys);
			ps.setString(1,customer.getFirst());
			ps.setString(2,customer.getLast());
			ps.setString(3,customer.getPhone());
			ps.setString(4,customer.getEmail());
			int addrid =customer.getAddress().getId();
			ps.setInt(5, addrid);
			System.out.println("customer.getAddress().getId() = " + customer.getAddress().getId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
			{
				log.trace("Successful insertion of customer");
				key = rs.getInt(1);
				conn.commit();
			
			}
			else {
				log.warn("Insertion of customer failed.");
				conn.rollback();
			}
		}
		catch(Exception e)
		{
			LogUtil.rollback(e,conn,CustomerOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,CustomerOracle.class);
			}
		}
		return key;
	}

	@Override
	public Customer getCustomer(Customer cust) {
		if(cust==null)
		{
			throw new NullArgumentException("cust can't be null");
		}
		try(Connection conn = cu.getConnection())
		{
			String sql = "select ID, address_id from Customer where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,cust.getId());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				log.trace("This is a customer");
				cust.setAddress(new Address(rs.getInt(1),null,null,null,null,null));
			}
			else
			{
				log.trace("This is not a customer");
				cust.setFirst(null);
				cust.setLast(null);
				cust.setId(0);
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e,CustomerOracle.class);
		}
		return cust;
	}
	@Override
	public Customer getCustomerById(Customer cust) {
		if(cust == null)
		{
			throw new NullArgumentException("i cannot be null");
		}
		try(Connection conn = cu.getConnection()){
			String sql = "select id, first_name, last_name, address, phone, email from customer where id=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, cust.getId());
			ResultSet rs = pstm.executeQuery();
			//id is unique, this query can only ever return a single result, so if is ok.
			if(rs.next())
			{
				log.trace("User found.");
				cust.setFirst(rs.getString("first_name"));
				cust.setLast(rs.getString("last_name"));
				cust.setId(rs.getInt("id"));
				cust.setEmail(rs.getString("email"));
				cust.setPhone(rs.getString("phone"));
				cust.setAddress(new Address(rs.getInt(1),null,null,null,null,null));
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, CustomerOracle.class);
		}
		
		return cust;
	}

	@Override
	public Set<Customer> getCustomers() {
		Set<Customer> custList = new HashSet<Customer>();
		try(Connection conn = cu.getConnection())
		{
			String sql = "Select id, address_id from customer";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while(rs.next())
			{
				Customer cust = new Customer();
				cust.setAddress(new Address(rs.getInt("address_id")));
				cust.setId(rs.getInt("id"));
				custList.add(cust);
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e,CustomerOracle.class);
		}
		return custList;
	}
	@Override
	public void updateCustomer(Customer customer)
	{
		log.trace("Updating customer from database.");
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			
			String sql = "update customer set address_id = ? where id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, customer.getAddress().getId());
			pstm.setInt(2, customer.getId());
			int number = pstm.executeUpdate();
			if(number!=1)
			{
				log.warn("customer not updated.");
				conn.rollback();
			}
			else
			{
				log.trace("customer updated.");
				conn.commit();
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e,CustomerOracle.class);
		}
	}

	@Override
	public void deleteCustomer(Customer customer) {
		log.trace("Removing customer from database.");
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			
			String sql = "delete from customer where id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, customer.getId());
			int number = pstm.executeUpdate();
			if(number!=1)
			{
				log.warn("customer not deleted.");
				conn.rollback();
			}
			else
			{
				log.trace("customer deleted.");
				conn.commit();
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e,CustomerOracle.class);
		}
	}
	/**
	 * This method will add authors to the book_author table for later retrieval
	 * @param conn the Connection object to the database
	 * @param book_id the id of the book to be associated with the author
	 * @param authors A list of authors to be associated with the book
	 * @return the number of authors successfully inserted.
	 * @throws SQLException
	 */



}
