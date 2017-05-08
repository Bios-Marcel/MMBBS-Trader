package com.msc.mmbbstrader.entities;

public class TradeEvent
{
	private final String sqlStatement;

	private final String beschreibung;

	private final int id;

	public TradeEvent(final int id, final String beschreibung, final String sqlStatement)
	{
		this.id = id;
		this.beschreibung = beschreibung;
		this.sqlStatement = sqlStatement;
	}

	/**
	 * @return the sqlStatement
	 */
	public String getSqlStatement()
	{
		return sqlStatement;
	}

	/**
	 * @return the name
	 */
	public String getBeschreibung()
	{
		return beschreibung;
	}

	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}
}
