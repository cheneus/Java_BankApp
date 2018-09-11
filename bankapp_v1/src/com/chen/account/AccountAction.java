package com.chen.account;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;

public class AccountAction {
	public static void main(String[] args) {
		ArrayList<Main> readAcc = (ArrayList<Main>) Serialization.read("accounts.dat");
		int accPosition = 0;
//		testing purpose.
		Scanner mainSc = new Scanner(System.in);
		System.out.println("1. Create an account");
		System.out.println("2. Login to account");
		System.out.println("3. I am an Admin");
		String mainScResp = mainSc.nextLine();
		switch (mainScResp) {
		case "1":
			create();
			System.out.println("Please wait for the admin to approve your account");
			break;
		case "2":
			login();
			break;
		case "3":
			System.out.println("Please enter Admin Password");
			String pass = mainSc.nextLine();
//			ArrayList<Main> readAcc = (ArrayList<Main>) Serialization.read("accounts.dat");
			System.out.println(readAcc);
			String usrPass = readAcc.get(0).getPassword();
			if (usrPass.equals(pass)) {
				for (int i = 1; i < readAcc.size(); i++) {
					System.out.println("Customer " + i + ",user :" + readAcc.get(i).getUsername());
					System.out.println(readAcc.get(i).getInfo());
					System.out.println("--------------------------");
				}
				System.out.println("Which account do you want to approve?");
				while (!mainSc.hasNextInt()) {
					System.out.println("Please enter a valid number");
					mainSc.next();
				}
				int opt = mainSc.nextInt();
				mainSc.nextLine();
				if (opt > 0) {
					System.out.println("Working on approval");
					readAcc.get(opt).setApproved(true);
					accPosition = opt;
				}
			} else {
				System.out.println("Wrong Password");
			}
//			readAcc.set(accPosition, readAcc);
			Serialization.save(readAcc, "accounts.dat");
			break;
		default:
			System.out.println("Not a valid selection");
			break;
		}
		System.out.println("Thank you for using Tactical Relocation");
	}

	private static void login() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter username");
		String usr = sc.nextLine();
		// String input password

		ArrayList<Main> readAcc = (ArrayList<Main>) Serialization.read("accounts.dat");
//		System.out.println(accRead.contains(username));
		Main usrAcc = null;
		int usrPosition = 0;
		for (int i = 0; i < readAcc.size(); i++) {
			Main temp = readAcc.get(i);
			if (temp.getUsername().equals(usr)) {
				System.out.println("Found your Account");
				if (temp.isApproved() == false) {
					System.out.println("Still waiting to be approved");
					break;
				} else {
					System.out.println("Enter Password");
					String pass = sc.nextLine();
					if (temp.getPassword().equals(pass)) {
						usrAcc = temp;
						usrPosition = i;
					}
				}
			}
		}
		if (usrPosition > 0) {
			Main updatedAcc = accountFunc(usrAcc);
			readAcc.set(usrPosition, updatedAcc);
			Serialization.save(readAcc, "accounts.dat");
		}

	}

	public static Main create() {
		Scanner sc = new Scanner(System.in);
		String saveDes = "accounts.dat";
		// String input
		System.out.println("Enter username");
		String username = sc.nextLine();

		// String input password
		System.out.println("Enter Password");
		String password = sc.nextLine();

		System.out.println("Is this a jointed account? Y/N");
		boolean jointed = true;
		boolean jointedAns = sc.nextLine().equalsIgnoreCase("y");

		System.out.println("Enter First Name");
		String fname = sc.nextLine();
		//
		System.out.println("Enter Last  Name");
		String lname = sc.nextLine();
		//
		System.out.println("Enter Phone Number");
		String phone = sc.nextLine();
		//
		System.out.println("Enter Date of Birth ie: yyyymmdd");
		String input = sc.nextLine();

		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);
		try {
			LocalDate localD = LocalDate.parse(input, f);
		} catch (Exception e) {
			System.out.println("Please Enter A Valid Date Format");
			System.out.println("Enter Date of Birth ie: yyyymmdd");
			input = sc.nextLine();
		}

		LocalDate localD = LocalDate.parse(input, f);
		String dob = localD.format(f);
		try {
			Serialization.read("accounts.dat");
		} catch (Exception e) {
			System.out.println(e);
		}
		ArrayList<Main> readAcc = (ArrayList<Main>) Serialization.read("accounts.dat");
		Main acc1 = new Main(username, password, jointedAns, fname, lname, phone, dob, 0d, false);
		System.out.println(readAcc);
		if (readAcc.equals(null)) {
			Serialization.save(acc1, saveDes);
		} else {
			readAcc.add(acc1);
			Serialization.save(readAcc, saveDes);
			System.out.println("---------------------");
			System.out.println(acc1.getUsername() + " is created");
		}
//		for (int i =0; i < readAcc.size(); i++) {
//			System.out.println(readAcc.get(i).getInfo());
//		}
		return acc1;
	}

	public static Main getUser(String target) {
		ArrayList<Main> readAcc = (ArrayList<Main>) Serialization.read("accounts.dat");
//		System.out.println(accRead.contains(username));
//		Main usrAcc = null;
		for (Main obj : readAcc) {
			if (obj.getUsername().equals(target)) {
				System.out.println("Found your Account");
				return obj;
			}
		}
		return null;
	}

	private static Main accountFunc(Main usrAcc) {
		Scanner sc = new Scanner(System.in);
		System.out.println("What would you like to do?");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. View Balance");
		System.out.println("4. Tranfer Funds");
		int accResp = sc.nextInt();
		double funds = 0;
		sc.nextLine();
		switch (accResp) {
		case 1:
			System.out.println("How much would you like to deposit?");
			funds = sc.nextInt();
			sc.nextLine();
			usrAcc.deposit(funds);
			break;
		case 2:
			System.out.println("How much would you like to withdraw?");
			funds = sc.nextInt();
			sc.nextLine();
			usrAcc.withdraw(funds);
			break;
		case 3:
			System.out.println(usrAcc.getBalance());
			break;
		case 4:
			System.out.println("How much?");
			funds = sc.nextInt();
			sc.nextLine();
			usrAcc.withdraw(funds);
			System.out.println("To Who");
			String target = sc.nextLine();
			Main receiver = getUser(target);
			receiver.deposit(funds);
			break;
		default:
			break;
		}
		System.out.println("Anything else? y/n");
		String cont = sc.nextLine();
		if (cont.equals("y")) {
			accountFunc(usrAcc);
		}
		return usrAcc;
	}
}
