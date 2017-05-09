package com.msc.mmbbstrader.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseConnection
{
	/**
	 * Database connection which will be kept alive for the life span of the program.
	 */
	private Connection connection = null;

	/**
	 * singleton instance
	 */
	private static DatabaseConnection instance;

	/**
	 * Private Constructor, to prevent third parties from creating instances.
	 */
	private DatabaseConnection()
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
	public static DatabaseConnection getInstance()
	{
		if (Objects.isNull(instance))
		{
			instance = new DatabaseConnection();
		}

		return instance;
	}

	public Connection getConnection()
	{
		return connection;
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
}
