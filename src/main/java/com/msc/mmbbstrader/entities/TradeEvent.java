package com.msc.mmbbstrader.entities;

public class TradeEvent
{
	private String sqlStatement;

	private String beschreibung;

	private int id;

	public TradeEvent(int id, String beschreibung, String sqlStatement)
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
