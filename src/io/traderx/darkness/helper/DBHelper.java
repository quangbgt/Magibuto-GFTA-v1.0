package io.traderx.darkness.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.traderx.darkness.config.ConfigLoader;

public class DBHelper {

	private static Connection connection = null;
	private static Statement statement = null;

	public static boolean connect() {
		if (connection == null) {
			try {
				String connectionString = "jdbc:mysql://" + ConfigLoader.get("host") + ":" + ConfigLoader.get("port") + "/"
						+ ConfigLoader.get("database") + "?useUnicode=true&characterEncoding=utf-8";
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(connectionString, ConfigLoader.get("user"),
						ConfigLoader.get("pass"));

				if (connection != null) {
					System.out.println("Connection was created!");
					return true;
				}
				return false;
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				return false;
			}
		} else {
			return true;
		}
	}

	public static boolean execute(String sql) {
		try {
			if (sql == null || sql == "") {
				return false;
			}
			statement = connection.createStatement();
			statement.execute(sql);
			return true;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	public static ResultSet executeQuery(String sql) {
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			return rs;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public static void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("##Connection was closed!");
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}