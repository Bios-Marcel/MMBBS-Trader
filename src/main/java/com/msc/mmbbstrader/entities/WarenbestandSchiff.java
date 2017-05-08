package com.msc.mmbbstrader.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class WarenbestandSchiff extends Warenbestand
{
	private final IntegerProperty schiffIdProperty = new SimpleIntegerProperty();

	public WarenbestandSchiff(final String warenName, final int warenId, final int schiffId)
	{
		super(warenName, warenId);
		schiffIdProperty.set(schiffId);
	}

	public int getSchiffId()
	{
		return schiffIdProperty.get();
	}

	public IntegerProperty schiffIdProperty()
	{
		return schiffIdProperty;
	}
}
