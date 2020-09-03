package kr.ac.shu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			
		}
	}
	
	public static Connection openConnection(){
		Connection con = null;
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:XE";	
			String id = "test";
			String passwd = "1234";
			con = DriverManager.getConnection(url, id, passwd);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException e) {
		}
		
		return con;
	}
	public static void closeConnection(Connection con){
		if(con != null){
			try {
				con.close();
				System.out.println("데이터베이스 종료 성공");
			} catch (SQLException e) {
			}
		}
	}
}
