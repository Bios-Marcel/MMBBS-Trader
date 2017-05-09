package com.msc.mmbbstrader.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.msc.mmbbstrader.entities.WarenbestandOrt;

public class WarenService implements Service
{

	public Collection<WarenbestandOrt> getWarenForOrt(final String ortName)
	{
		final Set<WarenbestandOrt> waren = new HashSet<>();

		try
		{
			final int ortId = Service.lookup(OrtService.class).getOrtIdByName(ortName);

			if (ortId == -1)
			{
				throw new SQLException("Ort was not found.");
			}

			final String warenForOrtQuery = "SELECT * FROM ort_has_ware INNER JOIN ware ON ware.id = ort_has_ware.ware_id WHERE ort_has_ware.ort_id = %i;";
			String.format(warenForOrtQuery, ortId);
			try (final ResultSet resultSet = executeQuery(warenForOrtQuery);)
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
}
