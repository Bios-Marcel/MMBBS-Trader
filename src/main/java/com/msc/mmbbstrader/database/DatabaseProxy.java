package com.msc.mmbbstrader.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.msc.mmbbstrader.controller.login.LoginResult;
import com.msc.mmbbstrader.entities.TradeEvent;
import com.msc.mmbbstrader.entities.Trader;
import com.msc.mmbbstrader.entities.WarenbestandOrt;

public class DatabaseProxy
{
	/**
	 * Database connection which will be kept alive for the life span of the program.
	 */
	private Connection connection = null;

	/**
	 * singleton instance
	 */
	private static DatabaseProxy instance;

	/**
	 * Private Constructor, to prevent third parties from creating instances.
	 */
	private DatabaseProxy()
	{
		// This exception should theoretically never occur, as long as the MySQL driver is within
		// the classpath.
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (final ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Returns the singleton instance of this class.
	 *
	 * @return {@link #instance}
	 */
	public static DatabaseProxy getInstance()
	{
		if (Objects.isNull(instance))
		{
			instance = new DatabaseProxy();
		}

		return instance;
	}

	public void connect(final String address, final String database, final String username, final String password) throws SQLException
	{
		closeConnection();
		connection = DriverManager.getConnection("jdbc:mysql://" + address + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8", username, password);
		connection.setCatalog(database);
	}

	private void closeConnection()
	{
		if (Objects.nonNull(connection))
		{
			try
			{
				connection.close();
			}
			catch (final SQLException failedCClosing)
			{
				// Ignore, has been closed already / was never open, so we don't care.
			}
		}
	}

	public Set<Trader> getActiveTraders()
	{
		final Set<Trader> traders = new HashSet<>();

		try
		{
			try (final ResultSet resultSet = executeQuery("SELECT * FROM trader;");)
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

	public LoginResult loginOrRegisterUser(final String username, final String password)
	{
		try
		{
			final String doesUserExistQuery = "SELECT COUNT(*) FROM trader WHERE name = '%s';";
			String.format(doesUserExistQuery, username);
			try (ResultSet userExistsResultSet = executeQuery(doesUserExistQuery);)
			{
				// Checks if the result set is empty
				if (!userExistsResultSet.first())
				{
					final String registerUserQuery = "INSERT INTO trader (name, pass) VALUES('%s', '%s');";
					String.format(registerUserQuery, username, password);
					executeQuery(registerUserQuery);
					return LoginResult.REGISTERED;
				}
				final String loginQuery = "SELECT * FROM trader WHERE name = '%s' AND pass = '%s';";
				String.format(loginQuery, username, password);
				try (ResultSet loginResultSet = executeQuery(loginQuery);)
				{
					return userExistsResultSet.first() ? LoginResult.LOGGED_IN : LoginResult.WRONG;
				}
			}
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}

		return LoginResult.WRONG;
	}

	public Collection<WarenbestandOrt> getWarenForOrt(final String ortName)
	{
		final Set<WarenbestandOrt> waren = new HashSet<>();

		try
		{
			final int ortId = getOrtIdByName(ortName);

			if (ortId == -1)
			{
				throw new SQLException("Ort was not found.");
			}

			final Statement statement = connection.createStatement();
			statement.setEscapeProcessing(true);
			final String warenForOrtQuery = "SELECT * FROM ort_has_ware INNER JOIN ware ON ware.id = ort_has_ware.ware_id WHERE ort_has_ware.ort_id = %i;";
			String.format(warenForOrtQuery, ortId);
			try (final ResultSet resultSet = statement.executeQuery(warenForOrtQuery);)
			{
				while (resultSet.next())
				{
					waren.add(new WarenbestandOrt(resultSet.getString("name"), resultSet.getInt("ware_id"), resultSet.getInt("ort_id"), resultSet.getInt("kapazitaet"), resultSet
							.getInt("preis"), resultSet.getInt("produktion"), resultSet.getInt("verbrauch")));
				}
			}
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}

		return waren;
	}

	// public Collection<WarenbestandSchiff> getWarenForSchiff(int traderId)
	// {
	// Set<WarenbestandOrt> waren = new HashSet<>();
	//
	// try
	// {
	// if (ortId == -1)
	// {
	// throw new SQLException("Ort was not found.");
	// }
	//
	// Statement statement = connect.createStatement();
	// statement.setEscapeProcessing(true);
	// String warenForOrtQuery = "SELECT * FROM ort_has_ware INNER JOIN ware ON ware.id =
	// ort_has_ware.ware_id WHERE
	// ort_has_ware.ort_id = %i;";
	// String.format(warenForOrtQuery, ortId);
	// try (final ResultSet resultSet = statement.executeQuery(warenForOrtQuery);)
	// {
	// while (resultSet.next())
	// {
	// waren.add(new WarenbestandOrt(resultSet.getString("name"), resultSet.getInt("ware_id"),
	// resultSet.getInt("ort_id"), resultSet.getInt("kapazitaet"), resultSet.getInt("preis"),
	// resultSet.getInt("produktion"), resultSet.getInt("verbrauch")));
	// }
	// }
	// }
	// catch (SQLException e)
	// {
	// e.printStackTrace();
	// }
	//
	// return waren;
	// }

	/**
	 * Returns the id of the ort having the given name.
	 *
	 * @param ortName
	 *            name to search for
	 * @return ort id or -1 if not found
	 */
	public int getOrtIdByName(final String ortName)
	{
		try
		{
			final String getOrtIdQuery = "SELECT id FROM ort WHERE name = '%s' LIMIT 1;";
			String.format(getOrtIdQuery, ortName);

			try (final ResultSet resultSet = executeQuery(getOrtIdQuery);)
			{
				if (resultSet.first())
				{
					return resultSet.getInt("id");
				}
			}
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}

		return -1;

	}

	public Collection<TradeEvent> getAllEvents()
	{
		final List<TradeEvent> events = new ArrayList<>();

		try
		{
			try (final ResultSet resultSet = executeQuery("SELECT statement, beschreibung, id FROM ereignis;");)
			{
				if (resultSet.first())
				{
					final TradeEvent event = new TradeEvent(resultSet.getInt("id"), resultSet.getString("beschreibung"), resultSet.getString("statement"));
					events.add(event);
				}
			}
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}

		return events;
	}

	public Optional<TradeEvent> getEventByID(final int id)
	{
		return getAllEvents().stream().filter(event -> event.getId() == id).findAny();
	}

	public void executeTradeEvent(final TradeEvent event)
	{
		executeUpdate(event.getSqlStatement());
	}

	private ResultSet executeQuery(final String query)
	{
		try
		{
			final Statement statement = connection.createStatement();
			statement.setEscapeProcessing(true);
			return statement.executeQuery(query);
		}
		catch (final SQLException e)
		{
			throw new RuntimeException(e);
		}
	}

	private int executeUpdate(final String query)
	{
		try
		{
			final Statement statement = connection.createStatement();
			statement.setEscapeProcessing(true);
			return statement.executeUpdate(query);
		}
		catch (final SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
}
