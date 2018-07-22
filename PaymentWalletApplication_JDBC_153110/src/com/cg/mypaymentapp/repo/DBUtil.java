package com.cg.mypaymentapp.repo;

import java.sql.*;

//Factory for connection
public class DBUtil 
{
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Connection conn=null;
	
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
			return conn;
		
			
		
	}
}

