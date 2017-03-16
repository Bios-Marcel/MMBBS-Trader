package com.msc.mmbbstrader.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Warenbestand
{
	private StringProperty nameProperty = new SimpleStringProperty();

	private IntegerProperty idProperty = new SimpleIntegerProperty();

	private IntegerProperty mengeProperty = new SimpleIntegerProperty();

	public Warenbestand(String warenName, int warenId)
	{
		nameProperty.set(warenName);
		idProperty.set(warenId);
	}

	public StringProperty nameProperty()
	{
		return nameProperty;
	}

	public String getName()
	{
		return nameProperty.getName();
	}

	public IntegerProperty idProperty()
	{
		return idProperty;
	}

	public int getId()
	{
		return idProperty.get();
	}

	public IntegerProperty mengeProperty()
	{
		return mengeProperty;
	}

	public int getMenge()
	{
		return mengeProperty.get();
	}

}
