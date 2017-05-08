package com.msc.mmbbstrader.controller;

import java.sql.SQLException;
import java.util.Optional;

import com.msc.mmbbstrader.database.DatabaseProxy;
import com.msc.mmbbstrader.entities.TradeEvent;
import com.msc.mmbbstrader.entities.Trader;
import com.msc.mmbbstrader.main.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController
{
	private final Stage stage;

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

	public MainController(final Stage stage)
	{
		this.stage = stage;
	}

	public void initialize()
	{
		final ObservableList<Trader> traders = FXCollections.observableArrayList();
		activeTraderTableView.setItems(traders);
	}

	@FXML
	public void onConnect()
	{
		final DatabaseProxy proxy = DatabaseProxy.getInstance();
		try
		{
			proxy.connect(serverTextField.getText(), databaseTextField.getText(), dbUsernameTextField.getText(), dbPasswordTextField.getText());

			activeTraderTableView.getItems().addAll(proxy.getActiveTraders());

			tradeTab.setDisable(false);
			masterTab.setDisable(false);
		}
		catch (final SQLException e)
		{
			final Alert alert = new Alert(AlertType.ERROR);
			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(Client.getIcon());
			alert.initOwner(stage);
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.setTitle("Connecting to database.");
			alert.setHeaderText("Wasn't able to establish database connection");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

	}

	@FXML
	private void executeCommand()
	{
		final String[] args = consoleInput.getText().split(" ");

		if (args.length >= 1)
		{
			switch (args[0])
			{
				case "erignisse":
				case "erigniss":
				case "event":
				case "events":
				{
					switch (args[1])
					{
						case "start":
						{
							final String idString = args[2];
							try
							{
								final Optional<TradeEvent> event = DatabaseProxy.getInstance().getEventByID(Integer.parseInt(idString));

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
							catch (final NumberFormatException e)
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
					break;
				}
				default:
				{
					printIntoMasterConsole("Command '" + args[0] + "' is unknown. Type help for a list of commands.");
					break;
				}
			}

			// If there is more than one arg in the console, it will be cleared
			consoleInput.clear();
		}
	}

	private void printIntoMasterConsole(final String text)
	{
		consoleOutput.insertText(consoleOutput.getText().length(), text + System.lineSeparator());
	}

}
