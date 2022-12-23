package com.student.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	private Connection conn;
	public DBManager() {
		try {
			Class.forName(DBConfig.DB_DRIVER);
			conn = DriverManager.getConnection(DBConfig.DB_URL,
					DBConfig.DB_USER, DBConfig.DB_PASSWD);
			conn.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close(PreparedStatement stmt, ResultSet rs) {
		try {
			if(rs != null)rs.close();
			if(stmt != null)stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Connection getConn() {
		return conn;
	}

}
