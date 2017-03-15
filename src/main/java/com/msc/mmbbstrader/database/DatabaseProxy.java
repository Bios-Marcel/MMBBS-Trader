package com.msc.mmbbstrader.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.msc.mmbbstrader.entities.Trader;

public class DatabaseProxy {

	private static DatabaseProxy instance;

	private Connection connect = null;

	private DatabaseProxy() {

	}

	public static DatabaseProxy getInstance() {
		if (Objects.isNull(instance)) {
			instance = new DatabaseProxy();
		}

		return instance;
	}

	public void connect(final String address, final String database, final String username, final String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://" + address + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8", username,
					password);
			connect.setCatalog(database);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<Trader> getActiveTraders() {
		final Set<Trader> traders = new HashSet<>();

		try {
			final Statement statement = connect.createStatement();
			statement.setEscapeProcessing(true);
			final ResultSet resultSet = statement.executeQuery("SELECT * FROM trader;");

			while (resultSet.next()) {
				traders.add(new Trader(resultSet.getInt("id"), resultSet.getString("name")));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return traders;
	}
}
