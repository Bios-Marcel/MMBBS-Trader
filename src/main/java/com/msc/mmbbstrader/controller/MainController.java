package com.msc.mmbbstrader.controller;

import java.util.Optional;

import com.msc.mmbbstrader.database.DatabaseProxy;
import com.msc.mmbbstrader.entities.TradeEvent;
import com.msc.mmbbstrader.entities.Trader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController
{
	// Tabs
	@FXML
	private Tab	tradeTab;
	@FXML
	private Tab	masterTab;

	// Connection Details
	@FXML
	private TextField		serverTextField;
	@FXML
	private TextField		databaseTextField;
	@FXML
	private TextField		dbUsernameTextField;
	@FXML
	private PasswordField	dbPasswordTextField;

	// Active Traders Table
	@FXML
	private TableView<Trader>				activeTraderTableView;
	@FXML
	private TableColumn<Trader, Integer>	columnId;
	@FXML
	private TableColumn<Trader, String>		columnName;

	// Master-Tab components
	@FXML
	private TextArea	consoleOutput;
	@FXML
	private TextField	consoleInput;

	public void initialize()
	{
		ObservableList<Trader> traders = FXCollections.observableArrayList();
		activeTraderTableView.setItems(traders);
	}

	@FXML
	public void onConnect()
	{
		DatabaseProxy proxy = DatabaseProxy.getInstance();
		proxy.connect(serverTextField.getText(), databaseTextField.getText(), dbUsernameTextField.getText(), dbPasswordTextField.getText());

		activeTraderTableView.getItems().addAll(proxy.getActiveTraders());

		tradeTab.setDisable(false);
		masterTab.setDisable(false);

	}

	@FXML
	private void executeCommand()
	{
		String[] args = consoleInput.getText().split(" ");

		if (args.length >= 1)
		{
			switch (args[0])
			{
				case "events":
				{
					switch (args[1])
					{
						case "start":
						{
							String idString = args[2];
							try
							{
								Optional<TradeEvent> event = DatabaseProxy.getInstance().getEventByID(Integer.parseInt(idString));

								if (event.isPresent())
								{
									DatabaseProxy.getInstance().executeTradeEvent(event.get());
									printIntoMasterConsole("Beschreibung des ausgeführten Events:");
									printIntoMasterConsole(event.get().getBeschreibung());
								}
								else
								{
									printIntoMasterConsole("Das Event mit der ID " + idString + " konnte nicht gefunden werden.");
								}

							}
							catch (NumberFormatException e)
							{
								printIntoMasterConsole("Event " + idString + " konnte nicht gefunden werden.");
							}
							break;
						}
						case "list":
						{
							printIntoMasterConsole("Liste aller Events:");
							DatabaseProxy.getInstance().getAllEvents().forEach(event -> printIntoMasterConsole(event.getId() + " | " + event.getBeschreibung()));
							break;
						}
					}
					break;
				}
				case "help":
				{
					printIntoMasterConsole("Available Event Commands:");
					printIntoMasterConsole("events list");
					printIntoMasterConsole(Character.PARAGRAPH_SEPARATOR + "lists all events that are present in the database");
				}
				default:
				{
					printIntoMasterConsole("Command '" + args[0] + "' is unknown. Type help for a list of commands.");
				}
			}

			// If there is more than one arg in the console, it will be cleared
			consoleInput.clear();
		}
	}

	private void printIntoMasterConsole(String text)
	{
		consoleOutput.insertText(consoleOutput.getText().length(), text + System.lineSeparator());
	}

}
