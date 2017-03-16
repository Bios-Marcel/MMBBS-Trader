package com.msc.mmbbstrader.controller;

import com.msc.mmbbstrader.database.DatabaseProxy;
import com.msc.mmbbstrader.entities.Trader;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController
{

	// Active Traders Table
	@FXML
	private TableView<Trader> activeTraderTableView;

	@FXML
	private TableColumn<Trader, Integer> columnId;

	@FXML
	private TableColumn<Trader, String> columnName;

	// Connection Details
	@FXML
	private TextField serverTextField;

	@FXML
	private TextField databaseTextField;

	// Tabs
	@FXML
	private Tab tradeTab;

	public void init()
	{
		activeTraderTableView.setItems(FXCollections.observableArrayList());
	}

	@FXML
	public void onConnect()
	{
		DatabaseProxy proxy = DatabaseProxy.getInstance();
		proxy.connect(serverTextField.getText(), databaseTextField.getText(), "mmbbs", "");

		activeTraderTableView.getItems().addAll(proxy.getActiveTraders());

		tradeTab.setDisable(false);
	}
}
