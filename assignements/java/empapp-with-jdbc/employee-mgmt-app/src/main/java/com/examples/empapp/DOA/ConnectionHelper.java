package com.examples.empapp.DOA;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {

	public static Connection conn = null;
	 
	static final String dburl = "jdbc:mysql://localhost:3306/empmgmt";
	
	public static Connection createConnection() {
		if(conn == null) {
			try {
				conn = DriverManager.getConnection(dburl,"root","55555");
				System.out.println("Connection created");
				return conn;
			}catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	
	
}
