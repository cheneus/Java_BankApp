package com.revature.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.beans.Login;

public class AccountManagement {
	public static final String FILENAME = "users.dat";
	private static Logger log = Logger.getLogger(AccountManagement.class);
	private static AccountManagement am = null;
	
	private Set<Login> logins;
	private Login currentLogin = null;
	
	private AccountManagement() {
		readUsers();
	}

	public static synchronized AccountManagement getInstance() {
		if (am == null) {
			am = new AccountManagement();
		}
		return am;
	}
	
	
	public Set<Login> getUsers() {
		return logins;
	}

	public void setUsers(Set<Login> users) {
		this.logins = users;
	}

	@SuppressWarnings("unchecked")
	private void readUsers() {
		this.logins = null;
		try (FileInputStream fs = new FileInputStream(AccountManagement.FILENAME);
				ObjectInputStream os = new ObjectInputStream(fs)) {
			logins = (Set<Login>) os.readObject();
		} catch (FileNotFoundException e) {
			// create the file
			log.warn("Creating the file, File not found.");
			File f = new File(AccountManagement.FILENAME);
			try(FileWriter fw = new FileWriter(f)){
				fw.write("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(this.logins == null) {
			this.logins = new HashSet<Login>();
			saveUsers();
		}
	}
	
	public void saveUsers() {
		try(FileOutputStream fs = new FileOutputStream(AccountManagement.FILENAME);
				ObjectOutputStream os = new ObjectOutputStream(fs)) {
			os.writeObject(this.logins);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Login getCurrentUser() {
		return currentLogin;
	}

	public void setCurrentUser(Login currentLogin) {
		this.currentLogin = currentLogin;
	}
}
