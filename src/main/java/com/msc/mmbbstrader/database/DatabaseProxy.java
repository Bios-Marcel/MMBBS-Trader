package com.msc.mmbbstrader.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.msc.mmbbstrader.controller.login.LoginResult;
import com.msc.mmbbstrader.entities.Trader;
import com.msc.mmbbstrader.entities.WarenbestandOrt;
import com.msc.mmbbstrader.entities.WarenbestandSchiff;

public class DatabaseProxy
{

	private static DatabaseProxy instance;

	private Connection connect = null;

	private DatabaseProxy()
	{

	}

	public static DatabaseProxy getInstance()
	{
		if (Objects.isNull(instance))
		{
			instance = new DatabaseProxy();
		}

		return instance;
	}

	public void connect(final String address, final String database, final String username, final String password)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://" + address + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8", username, password);
			connect.setCatalog(database);
		}
		catch (SQLException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<Trader> getActiveTraders()
	{
		final Set<Trader> traders = new HashSet<>();

		try
		{
			final Statement statement = connect.createStatement();
			statement.setEscapeProcessing(true);
			try (final ResultSet resultSet = statement.executeQuery("SELECT * FROM trader;");)
			{
				while (resultSet.next())
				{
					traders.add(new Trader(resultSet.getInt("id"), resultSet.getString("name")));
				}
			}

		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}
		return traders;
	}

	public LoginResult loginOrRegisterUser(String username, String password)
	{
		try
		{
			final Statement statement = connect.createStatement();
			statement.setEscapeProcessing(true);
			String doesUserExistQuery = "SELECT COUNT(*) FROM trader WHERE name = '%s';";
			String.format(doesUserExistQuery, username);
			try (ResultSet userExistsResultSet = statement.executeQuery(doesUserExistQuery);)
			{
				// Checks if the result set is empty
				if (!userExistsResultSet.first())
				{
					String registerUserQuery = "INSERT INTO trader (name, pass) VALUES('%s', '%s');";
					String.format(registerUserQuery, username, password);
					statement.executeQuery(registerUserQuery);
					return LoginResult.REGISTERED;
				}
				else
				{
					String loginQuery = "SELECT * FROM trader WHERE name = '%s' AND pass = '%s';";
					String.format(loginQuery, username, password);
					try (ResultSet loginResultSet = statement.executeQuery(loginQuery);)
					{
						return userExistsResultSet.first() ? LoginResult.LOGGED_IN : LoginResult.WRONG;
					}
				}
			}
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}

		return LoginResult.WRONG;
	}

	public Collection<WarenbestandOrt> getWarenForOrt(String ortName)
	{
		Set<WarenbestandOrt> waren = new HashSet<>();

		try
		{
			Statement statement = connect.createStatement();
			statement.setEscapeProcessing(true);
			String warenForOrtQuery = "SELECT * FROM ort_has_ware INNER JOIN ort ON ort.id = ort_has_ware.ort_id WHERE ort.name = '%s';";
			String.format(warenForOrtQuery, ortName);
			try (final ResultSet resultSet = statement.executeQuery(warenForOrtQuery);)
			{
				while (resultSet.next())
				{
					waren.add(new WarenbestandOrt());
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return waren;
	}

	public Collection<WarenbestandSchiff> getWarenForSchiff(int traderId)
	{

	}

}
