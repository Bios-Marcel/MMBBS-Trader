package com.msc.mmbbstrader.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.msc.mmbbstrader.database.DatabaseConnection;

public interface Service
{
	public static Map<Class<? extends Service>, ? extends Service> services = new HashMap<>();

	default ResultSet executeQuery(final String query) throws SQLException
	{
		final Statement statement = DatabaseConnection.getInstance().getConnection().createStatement();
		statement.setEscapeProcessing(true);
		return statement.executeQuery(query);
	}

	default int executeUpdate(final String query) throws SQLException
	{
		final Statement statement = DatabaseConnection.getInstance().getConnection().createStatement();
		statement.setEscapeProcessing(true);
		return statement.executeUpdate(query);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Service> T lookup(final Class<T> service)
	{
		return (T) services.get(service);
	}
}
