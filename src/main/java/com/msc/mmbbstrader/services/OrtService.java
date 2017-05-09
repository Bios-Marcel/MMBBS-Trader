package com.msc.mmbbstrader.services;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrtService implements Service
{
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

}
