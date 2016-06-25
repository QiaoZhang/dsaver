package com.eptd.dsaver.dbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBOperation {
	// global static connection
	static Connection conn;
	// global static statement
	static Statement st;
	
	public DBOperation(){
		conn = this.getConnection();
	}
	
	/*** Function of connecting database ***/
	private static Connection getConnection() {
		Connection con = null; // create temporary connection
		try {
			Class.forName("com.mysql.jdbc.Driver"); // load JDBC driver
			// create database connection
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/eptd", "root",
					"loveon1225");
		} catch (Exception e) {
			System.out.println("Database connection failed: " + e.getMessage());
		}
		return con;
	}
}
