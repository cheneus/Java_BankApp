package com.revature.data;

import java.util.Set;

import com.revature.beans.Login;

public interface LoginDAO {
	/**
	 * Returns the id of a Login object inserted into the database.
	 * 
	 * @param Login the Login object to be inserted
	 * @return the id of the Login object inserted
	 */
	public int addLogin(Login login);
	
	/**
	 * returns a login object from the database
	 * 
	 * @param Loginname the Loginname of the Login
	 * @param password the password of the Login
	 * @return the Login from the database that matches the Loginname and password
	 */	
	public Login getLogin(String username, String password);
	/**
	 * returns a login object from the database
	 * 
	 * @param u previously created Login object for updating with Login information
	 * @return the Login from the database that matches the Loginname and password
	 */
	public Set<Login> getLogins();
	/**
	 * returns a login object from the database
	 * 
	 * @param u previously created Login object for updating with Login information
	 * @return the Login from the database that matches the Loginname and password
	 */
	public Login getLoginById(Login l);
	/**
	 * deletes a Login from the database
	 * 
	 * @param Login the Login to be deleted
	 */
	public void deleteLogin(Login login);
	
	/**
	 * updates a Login in the database
	 * 
	 * @param Login the Login to be updated
	 */
	public void updateLogin(Login login);
}
