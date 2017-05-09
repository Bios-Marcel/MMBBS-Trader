package com.msc.mmbbstrader.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.msc.mmbbstrader.entities.TradeEvent;

public class TradeEventService implements Service
{
	public Collection<TradeEvent> getAllEvents()
	{
		final List<TradeEvent> events = new ArrayList<>();

		try
		{
			try (final ResultSet resultSet = executeQuery("SELECT statement, beschreibung, id FROM ereignis;");)
			{
				while (resultSet.next())
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
		try
		{
			executeUpdate(event.getSqlStatement());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
