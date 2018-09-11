package com.revature.services;

import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.beans.Customer;
import com.revature.data.AddressDAO;
import com.revature.data.BankAppDAOFactory;
import com.revature.data.CustomerDAO;

public class CustomerServiceOracle implements CustomerService {
	private Logger log = Logger.getLogger(CustomerServiceOracle.class);
	private BankAppDAOFactory bf = BankAppDAOFactory.getInstance();
	private CustomerDAO cd = bf.getCustomerDAO();
	private AddressDAO addrdao = bf.getAddressDAO();
	
	@Override
	public Customer getCustomer(String username, String password) {
		Customer cust = new Customer(0, username, password);
		cust = (Customer) cd.getCustomer(cust);
		if(cust.getId()==0) {
			log.warn("No customer found");
			return null;
		}
		cust = cd.getCustomer(cust);
		cust.setAddress(addrdao.getAddress(cust.getAddress().getId()));
		return cust;
	}

	@Override
	public Customer getCustomerById(int i) {
		Customer cust = new Customer(i);
		cust = (Customer) cd.getCustomerById(cust);
		if(cust.getId()==0) {
			log.trace("No customer found");
			return null;
		}
		cust.setAddress(addrdao.getAddress(cust.getAddress().getId()));

		return cust;
	}

	@Override
	public Set<Customer> getCustomers() {
		Set<Customer> custList = cd.getCustomers();
		for(Customer c : custList) {
			cd.getCustomerById(c);
			c.setAddress(addrdao.getAddress(c.getAddress().getId()));

		}
		return custList;
	}

	@Override
	public void deleteCustomer(Customer cust) {
		cd.deleteCustomer(cust);
	}

	@Override
	public void updateCustomer(Customer cust) {
		addrdao.updateAddress(cust.getAddress());
		cd.updateCustomer(cust);
	}

	@Override
	public void addCustomer(Customer cust) {
		cust.getAddress().setId(addrdao.addAddress(cust.getAddress()));
		cd.addCustomer(cust);
	}

}
