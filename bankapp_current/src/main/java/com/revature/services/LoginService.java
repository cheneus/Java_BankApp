package com.revature.services;

import java.util.Set;

import com.revature.beans.Login;

public interface LoginService {
	public Set<Login> getLogins();
	public Login getLogin(String username, String password);
	public void updateLogin(Login l);
	public void deleteLogin(Login l);
	public void addLogin(Login l);
}
