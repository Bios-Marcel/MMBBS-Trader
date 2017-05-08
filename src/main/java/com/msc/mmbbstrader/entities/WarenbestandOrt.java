package com.msc.mmbbstrader.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class WarenbestandOrt extends Warenbestand
{

	private final IntegerProperty	ortIdProperty		= new SimpleIntegerProperty();
	private final IntegerProperty	kapazitaetProperty	= new SimpleIntegerProperty();
	private final IntegerProperty	preisProperty		= new SimpleIntegerProperty();
	private final IntegerProperty	produktionProperty	= new SimpleIntegerProperty();
	private final IntegerProperty	verbrauchProperty	= new SimpleIntegerProperty();

	public WarenbestandOrt(final String warenName, final int wareId, final int ortId, final int kapazitaet, final int preis, final int produktion, final int verbrauch)
	{
		super(warenName, wareId);
		ortIdProperty.set(ortId);
		kapazitaetProperty.set(kapazitaet);
		preisProperty.set(preis);
		produktionProperty.set(produktion);
		verbrauchProperty.set(verbrauch);
	}

	public final IntegerProperty ortIdProperty()
	{
		return this.ortIdProperty;
	}

	public final int getOrtId()
	{
		return this.ortIdProperty().get();
	}

	public final IntegerProperty kapazitaetProperty()
	{
		return this.kapazitaetProperty;
	}

	public final int getKapazitaet()
	{
		return this.kapazitaetProperty().get();
	}

	public final IntegerProperty preisProperty()
	{
		return this.preisProperty;
	}

	public final int getPreis()
	{
		return this.preisProperty().get();
	}

	public final IntegerProperty produktionProperty()
	{
		return this.produktionProperty;
	}

	public final int getProduktion()
	{
		return this.produktionProperty().get();
	}

	public final IntegerProperty verbrauchProperty()
	{
		return this.verbrauchProperty;
	}

	public final int getVerbrauch()
	{
		return this.verbrauchProperty().get();
	}

}
