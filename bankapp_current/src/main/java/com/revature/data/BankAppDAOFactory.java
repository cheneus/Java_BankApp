package com.revature.data;

public class BankAppDAOFactory {
	private static BankAppDAOFactory bf = new BankAppDAOFactory();
	private static final String TYPE = "Oracle";
	private BankAppDAOFactory() {
		super();
	}
	public static synchronized BankAppDAOFactory getInstance() {
		if(bf==null) {
			bf = new BankAppDAOFactory();
		}
		return bf;
	}
	public AccountDAO getAuthorDAO() {
		switch(TYPE) {
		case "Oracle": return new AccountOracle();
		default: throw new RuntimeException("Could not determine DAO type");
		}
	}
	public LoginDAO getLoginDAO() {
		switch(TYPE) {
		case "Oracle": return new LoginOracle();
		default: throw new RuntimeException("Could not determine DAO type");
		}
	}
	public AccCustDAO getAccCustDAO() {
		switch(TYPE) {
		case "Oracle": return new AccCustOracle();
		default: throw new RuntimeException("Could not determine DAO type");
		}
	}
	public AddressDAO getAddressDAO() {
		switch(TYPE) {
		case "Oracle": return new AddressOracle();
		default: throw new RuntimeException("Could not determine DAO type");
		}
	}
	public EmployeeDAO getEmployeeDAO() {
		switch(TYPE) {
		case "Oracle": return new EmployeeOracle();
		default: throw new RuntimeException("Could not determine DAO type");
		}
	}
	public CustomerDAO getCustomerDAO() {
		switch(TYPE) {
		case "Oracle": return new CustomerOracle();
		default: throw new RuntimeException("Could not determine DAO type");
		}
	}
	public TransactionDAO getTransactionDAO() {
		switch(TYPE) {
		case "Oracle": return new TransactionOracle();
		default: throw new RuntimeException("Could not determine DAO type");
		}
	}
}
