package com.msc.mmbbstrader.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.msc.mmbbstrader.controller.login.LoginResult;
import com.msc.mmbbstrader.entities.Trader;

public class TraderService implements Service
{

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

}
