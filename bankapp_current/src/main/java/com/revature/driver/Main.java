package com.revature.driver;

import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.beans.AccCust;
import com.revature.beans.Account;
import com.revature.beans.Address;
import com.revature.beans.Admin;
import com.revature.beans.Customer;
import com.revature.beans.Login;
import com.revature.data.AccCustOracle;
import com.revature.data.AccountOracle;
import com.revature.data.AddressOracle;
import com.revature.data.BankAppDAOFactory;
import com.revature.data.CustomerOracle;
import com.revature.data.LoginOracle;
import com.revature.data.TransactionOracle;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class Main {
	private static ConnectionUtil cu = ConnectionUtil.getInstance();
	private static BankAppDAOFactory bf = BankAppDAOFactory.getInstance();
	private static Logger log = Logger.getLogger(Main.class);
	private static Scanner scan = new Scanner(System.in);
	static AccountOracle acc_o = new AccountOracle();
	static AddressOracle addr_o = new AddressOracle();
	static CustomerOracle cust_o = new CustomerOracle();
	static LoginOracle login_o = new LoginOracle();
	static AccCustOracle acc_cust_o = new AccCustOracle();
	static TransactionOracle trans_o = new TransactionOracle();

	public static void main(String[] args) {
//		-----------------------------------------
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Create an account");
		System.out.println("2. Login to account");
		System.out.println("3. ....GoodBye");
		String mainScResp = sc.nextLine();
		switch (mainScResp) {
		case "1":
			register();
			System.out.println("Please wait for the admin to approve your account");
			break;
		case "2":
			login();
			break;
		case "3":
			System.out.println("GOOD BYE & HAVE A SWELL DAY!");
			System.exit(0);
			break;
		default:
			break;
		}
		System.out.println("Thank you for using Tactical Relocation");
	}

	private static void register() {
		Login l = new Login();
		Customer c = new Customer();
		Address a = new Address();
		Account acc = new Account();
		// Scanners
		System.out.println("Enter username: ");
		String s = scan.nextLine();
		l.setUsername(s);
		System.out.println("Enter password: ");
		s = scan.nextLine();
		l.setPassword(s);
		System.out.println("Enter first name: ");
		s = scan.nextLine();
		c.setFirst(s);
		System.out.println("Enter last name: ");
		s = scan.nextLine();
		c.setLast(s);
		System.out.println("Enter phone: ");
		s = scan.nextLine();
		c.setPhone(s);
		System.out.println("Enter email: ");
		s = scan.nextLine();
		c.setEmail(s);
		System.out.println("Enter address line 1: ");
		s = scan.nextLine();
		a.setLineOne(s);
		System.out.println("Enter address line 2 (Suite/APT): ");
		s = scan.nextLine();
		a.setLineTwo(s);
		System.out.println("Enter city: ");
		s = scan.nextLine();
		a.setCity(s);
		System.out.println("Enter state: ");
		s = scan.nextLine();
		a.setState(s);
		System.out.println("Enter zip: ");
		s = scan.nextLine();
		a.setZip(s);
		int new_a = addr_o.addAddress(a);
		a.setId(new_a);
		c.setAddress(a);
		int new_c = cust_o.addCustomer(c);
		c.setId(new_c);
		l.setCust_id(c);
		login_o.addLogin(l);
		System.out.println("Your account needs to be approved. Please check back later");
		acc.setApproved("false");
		acc.setBalance(0d);
		acc.setAccount_type("checking");
		int new_acc = acc_o.addAccount(acc);
		acc.setId(new_acc);
		acc_cust_o.addAccCust(acc, c);
//		Address a2 = new Address();
//		Customer c2 = new Customer();
//		Login l2 = new Login();
//		Account acc2 = new Account();
//		a2.setLineOne("123test");
//		a2.setState("IL");
//		a2.setZip("60220");
//		a2.setCity("Chicago");
//		c2.setFirst("firstQQ");
//		c2.setLast("lastFFF");
//		c2.setEmail("fasdft@fist");
//		c2.setPhone("3123456789");
//		c2.setAddress(a2);
//		c2.setId(new_c);
//		l2.setUsername("abcd");
//		l2.setPassword("12345");
//		l2.setCust_id(c2);
//		a2.setId(new_a);	
	}

	private static void login() {
		String user = null;
		String pass = null;
		Customer cust = null;
		Scanner scan = new Scanner(System.in);
		System.out.println("Username : ");
		user = scan.nextLine().toLowerCase();
		System.out.println("Password : ");
		pass = scan.nextLine();
		System.out.println(user);
//		getLogin(user,pass);
		cust = getCustomer(user, pass);
		System.out.println(cust);
		if (cust == null) {
			System.out.println("Login Fail");
			System.out.println("Would you like to try again? (y/n)");
			String input = scan.nextLine();
			if ("y".equals(input)) {
				login();
			} else {
				System.out.println("Goodbye");
			}
		} else {
			if ("admin".equals(cust.getLogin_type())) {
				System.out.println("Hi.The Great Admin");
				System.out.println("Proceed into Admin? (y/n)");
				String adminAns = scan.nextLine();
				switch (adminAns) {
				case "y":
					Admin adm = adminUp(cust);
					adminScanner(adm);
					break;
				default:
					customerEntry(cust);
					break;
				}
			} else {
				customerEntry(cust);
			}
		}
	}

	private static Customer getCustomer(String user, String pass) {
		// are no longer allowed to use Statement
//		Address a = null;
//		Customer cust = null;

		Address a = null;
		Customer cust = null;
		try (Connection conn = cu.getConnection()) {
			String sql = "select customer.id, customer.firstname,customer.lastname,customer.address, customer.email, customer.phone, login.username, login.password, login.login_type, address.lineone, address.linetwo, address.city, address.state, address.zip from (customer join login on customer.id = login.customer_id) join address  on address.id = customer.address where login.username = ? and login.password = ?";
//			String sql = "select customer.id, customer.firstname,customer.lastname,customer.address, customer.email, customer.phone, login.username, login.password, login.login_type "
//					+ "from (customer join login on customer.id = login.customer_id) where login.username=? and login.password=?";
//			String sql = "select username, password, customer_id, login_type from login where username = ? and password= ?";
			log.trace(sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
//			log.trace(stmt.executeQuery());
//			
			if (rs.next()) {
				log.trace("User found!");
				a = new Address();
				cust = new Customer();
				cust.setId(rs.getInt(1));
				cust.setFirst(rs.getString(2));
				cust.setLast(rs.getString(3));
				a.setId(rs.getInt(4));
				cust.setEmail(rs.getString(5));
				cust.setPhone(rs.getString(6));
				cust.setUsername(rs.getString(7));
				cust.setPassword(rs.getString(8));
				cust.setLogin_type(rs.getString(9));
				a.setLineOne(rs.getString(10));
				a.setLineTwo(rs.getString(11));
				a.setCity(rs.getString(12));
				a.setState(rs.getString(13));
				a.setZip(rs.getString(14));
				cust.setAddress(a);
			}
		} catch (SQLException e) {
			LogUtil.logException(e, Main.class);
		}
		return cust;
	}

	private static Login getLogin(String user, String pass) {
		// are no longer allowed to use Statement
		Login u = null;

		try (Connection conn = cu.getConnection()) {
			String sql = "select * from login where username =? and password=?";
			log.trace(sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "admin");
			stmt.setString(2, "password");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.trace("User found!");
				u = new Login();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, Main.class);
		}

		return u;
	}

	private static void adminScanner(Admin adm) {
		Scanner scan = new Scanner(System.in);
		System.out.println("1. See all accounts");
		System.out.println("2. Approve account");
		String mainScResp = scan.nextLine();
		switch (mainScResp) {
		case "1":
			System.out.println(adm.getAccounts());

			break;
		case "2":
			System.out.println(adm.getUnapprovedAccounts());
			System.out.println("Please key in the account id");
			int sc_i = scan.nextInt();
			scan.nextLine();
			acc_o.approveAccount(sc_i);
			break;
		default:
			break;
		}
		System.out.println("Anything else? y/n");
		String cont = scan.nextLine();
		if ("y".equals(cont)) {
			adminScanner(adm);
		}
		scan.close();
	}

	private static Admin adminUp(Customer c) {
		Admin adm = new Admin();
		adm.setCust_id(c.getCust_id());
		adm.setFirst(c.getFirst());
		adm.setLast(c.getLast());
		adm.setEmail(c.getEmail());
		adm.setPhone(c.getPhone());
		adm.setUsername(c.getUsername());
		adm.setPassword(c.getPassword());
		adm.setLogin_type(c.getLogin_type());
		adm.setAddress(c.getAddress());
		adm.setAccounts(acc_o.getAccounts());
		adm.setUnapprovedAccounts(acc_o.getUnapprovedAccounts());
		return adm;
	}

	private static void applyAcc(Customer cust) {
		Account acc = null;
		int new_acc = 0;
		System.out.println("What account type would you like to open?");
		System.out.println("1. Saving");
		System.out.println("2. Joint");
		String accInput = scan.nextLine();
		switch (accInput) {
		case "1":
			acc = new Account();
			acc.setApproved("false");
			acc.setBalance(0d);
			acc.setAccount_type("saving");
			new_acc = acc_o.addAccount(acc);
			acc.setId(new_acc);
			acc_cust_o.addAccCust(acc, cust);
			break;
		case "2":
			acc = new Account();
			acc.setApproved("false");
			acc.setBalance(0d);
			acc.setAccount_type("joint");
			new_acc = acc_o.addAccount(acc);
			acc.setId(new_acc);
			acc_cust_o.addAccCust(acc, cust);
			manageAccounts(cust);
			if (acc != null) {
				System.out.println("Success");
			}
			break;
		default:
			break;
		}
		System.out.println("Please wait for your account to be approved");
	}

	private static void customerEntry(Customer cust) {
		System.out.println("1. See personal information");
		System.out.println("2. Handle ya'' money!");
		System.out.println("3. Add an account");
		String accInput = scan.nextLine();
		switch (accInput) {
		case "1":
			System.out.println("First Name: " + cust.getFirst());
			System.out.println("Last Name: " + cust.getLast());
			System.out.println("Phone: " + cust.getPhone());
			System.out.println("Email: " + cust.getEmail());
			System.out.println("Address: " + cust.getAddress());
			break;
		case "2":
			manageAccounts(cust);
			break;
		case "3":
			applyAcc(cust);
			break;
		default:
			break;
		}
		System.out.println("Anything else? Dear Customer.. (y/n)");
		String cont = scan.nextLine();
		if (cont.equals("y")) {
			customerEntry(cust);
		}
	}

	private static void manageAccounts(Customer cust) {
		List<AccCust> acc_cust = acc_cust_o.AccCustByCustId(cust);
		List<Account> acc_list = new ArrayList<Account>();
		for (AccCust acc_cs : acc_cust) {
			if ("true".equals(acc_cs.getAccount_id().getApproved())) {
				acc_list.add(acc_cs.getAccount_id());
			}
		}
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < acc_list.size(); i++) {
			System.out.println((i + 1) + ") Account ID: " + acc_list.get(i).getId() + " Type:"
					+ acc_list.get(i).getAccount_type());
		}
		System.out.println("Picking an account.");
		int chosen = sc.nextInt();
		sc.nextLine();
		switch (chosen) {
		case 1:
			accFuncScan(acc_cust.get(0).getAccount_id());
			break;
		case 2:
			accFuncScan(acc_cust.get(1).getAccount_id());
			break;
		case 3:
			accFuncScan(acc_cust.get(2).getAccount_id());
			break;
		default:
			accFuncScan(acc_cust.get(0).getAccount_id());
			break;

		}
	}

	private static void accFuncScan(Account account) {
		Scanner sc = new Scanner(System.in);
		System.out.println("What would you like to do?");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. View Balance");
		System.out.println("4. Tranfer Funds");
		Account temp = account;
		Account acc = account;
		double b_acc = 0;
		int accResp = sc.nextInt();
		double funds = 0;
		sc.nextLine();
		switch (accResp) {
		case 1:
			System.out.println("How much would you like to deposit?");
			funds = sc.nextDouble();
			sc.nextLine();
			b_acc = acc.deposit(funds);
			if (acc.getBalance() == b_acc) {
				trans_o.addTransaction(acc, funds, "credit");
				System.out.println("Success");
			}
			break;
		case 2:
			System.out.println("How much would you like to withdraw?");
			funds = sc.nextDouble();
			sc.nextLine();
			b_acc = acc.withdraw(funds);
			if (acc.getBalance() == b_acc) {
				trans_o.addTransaction(acc, funds, "debit");
				System.out.println("Success");
			}
			break;
		case 3:
			System.out.println("Your Balance is " + acc.getBalance());
			double balance = acc.getBalance();
			acc.setBalance(balance);
			break;
		case 4:
			System.out.println("How much?");
			funds = sc.nextDouble();
			sc.nextLine();
			b_acc = acc.withdraw(funds);
			if (acc.getBalance() == b_acc) {
				System.out.println("To Who");
				int target = sc.nextInt();
				Account acc2 = acc_o.getAccount(target);
				if (acc2.getId() == target) {
					System.out.println("inside");
					acc2.setBalance(acc.deposit(funds));
					acc_o.updateBalance(acc2);
					trans_o.addTransaction(acc2, funds, "credit");
					trans_o.addTransaction(acc, funds, "debit");
					break;
				}
			}
//			receiver.deposit(funds);
			break;
		default:
			System.out.println("Invalid Input");
			break;
		}
		acc_o.updateBalance(acc);
		System.out.println("Anything else you wanna do with your money? y/n");
		String cont = sc.nextLine();
		if (("y").equals(cont)) {
			accFuncScan(acc);
		}
	}

//	private static void firstAttempt() {
//		String sql = "select * from login";
//		//String sql = "select id, title from book";
//		try (Connection conn = cu.getConnection()) {
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(sql);
//			while(rs.next()) {
//				//log.trace(rs.getInt("id")+" | "+rs.getString("title"));
//				log.trace(rs.getInt(1)+" | "+rs.getString(2));
//			}
//		} catch (SQLException e) {
//			LogUtil.logException(e, Main.class);
//		}
//	}

}
