package com.my.javastore;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

	// static reference to itself
	private static MyConnection instance = new MyConnection();
	public static final String URL = "jdbc:mysql://localhost/store";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

	// private constructor
	private MyConnection() {
//		try {
//			// Step 2: Load MySQL Java driver
//			Class.forName(DRIVER_CLASS);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
	}

	private Connection createConnection() {

		Connection connection = null;
		try {
			// Step 3: Establish Java MySQL connection
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to Connect to Database.");
		}
		return connection;
	}

	public static Connection getConnect() {
		return instance.createConnection();
	}

	public static void fillBrand(DefaultListModel<String> mBrand) {
		mBrand.addElement("Choose");
		mBrand.addElement("Kissan");
		mBrand.addElement("Bourbon");
		mBrand.addElement("Sunfeast");
		mBrand.addElement("Pepsi");
		mBrand.addElement("Coca-Cola");
		mBrand.addElement("Britania");
		mBrand.addElement("Nestele");
		mBrand.addElement("Detol");
		mBrand.addElement("Lux");
		mBrand.addElement("Parkevenue");
		mBrand.addElement("WildStone");
		mBrand.addElement("Haldiram");
		mBrand.addElement("MDH");
		mBrand.addElement("Hawells");
		mBrand.addElement("BMW");
		mBrand.addElement("Audi");
	}

	public static void fillPBrand(DefaultComboBoxModel mBrand) {
		mBrand.addElement("Choose");
		mBrand.addElement("Kissan");
		mBrand.addElement("Bourbon");
		mBrand.addElement("Sunfeast");
		mBrand.addElement("Pepsi");
		mBrand.addElement("Coca-Cola");
		mBrand.addElement("Britania");
		mBrand.addElement("Nestele");
		mBrand.addElement("Detol");
		mBrand.addElement("Lux");
		mBrand.addElement("Parkevenue");
		mBrand.addElement("WildStone");
		mBrand.addElement("Haldiram");
		mBrand.addElement("MDH");
		mBrand.addElement("Hawells");

		mBrand.addElement("BMW");
		mBrand.addElement("Audi");
	}
}