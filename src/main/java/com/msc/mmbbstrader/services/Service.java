package com.msc.mmbbstrader.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.msc.mmbbstrader.database.DatabaseConnection;

public interface Service
{
	public static final Map<Class<? extends Service>, Service> services = new HashMap<>();

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
		if (Objects.isNull(services.get(service)))
		{
			try
			{
				services.put(service, service.newInstance());
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				throw new RuntimeException("Error instanciating service", e);
			}
		}
		return (T) services.get(service);
	}
}
