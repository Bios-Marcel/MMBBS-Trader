package com.msc.mmbbstrader.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class WarenbestandSchiff extends Warenbestand
{
	private IntegerProperty schiffIdProperty = new SimpleIntegerProperty();

	public WarenbestandSchiff(String warenName, int warenId, int schiffId)
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
