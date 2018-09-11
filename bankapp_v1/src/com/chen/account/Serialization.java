package com.chen.account;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serialization {
	public static Serializable read(String filename) {
		Serializable s = null;
		
		try(
				FileInputStream fs = new FileInputStream(filename);
				ObjectInputStream os = new ObjectInputStream(fs)) {
			s = (Serializable) os.readObject();
		} catch (FileNotFoundException e) {
			Main admin = new Main ("admin", "password", false, "admin", "alpha", "0000000000", "0000-00-00", 0d, true);
			List<Main> acc = new ArrayList<Main>();
			acc.add(admin);
			save((ArrayList<Main>) acc, "accounts.dat");
//			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static void save(Serializable s, String filename) {
		try(FileOutputStream fs = new FileOutputStream(filename);
				ObjectOutputStream os = new ObjectOutputStream(fs)) {
			os.writeObject(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
