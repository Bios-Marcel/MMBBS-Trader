package com.msc.mmbbstrader.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Trader {
	private IntegerProperty idProperty = new SimpleIntegerProperty();

	private StringProperty nameProperty = new SimpleStringProperty();

	public Trader(int id, String name) {
		idProperty.set(id);
		nameProperty.set(name);
	}

	public IntegerProperty idProperty() {
		return idProperty;
	}

	public Integer getId() {
		return idProperty.get();
	}

	public StringProperty nameProperty() {
		return nameProperty;
	}

	public String getName() {
		return nameProperty.get();
	}
}