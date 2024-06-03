package com.safechain.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;
import com.safechain.utils.ScrollPaneUtils;
import com.safechain.utils.TransferData;
import com.safechain.utils.ViewNavigator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Random;

public class SecondaryController {
	@FXML
	private TextField messageField;
	@FXML
	private Button sendMessageBtn;
	@FXML
	private VBox vboxForBtn;
	@FXML
	private VBox messageContainer = new VBox();
	@FXML
	private ScrollPane scrollPane = new ScrollPane(messageContainer);

	@FXML
	private ListView<String> usersListView;

	private SerialPort serialPort;
	private PrintWriter sensorWriter;

	public void initialize() throws IOException, InterruptedException {

		TransferData transferData = ViewNavigator.getData();
        serialPort = transferData.getSerialPort();
        sensorWriter = transferData.getSensorWriter();

		if (serialPort != null && serialPort.isOpen()) {
			ObservableList<String> names = FXCollections.observableArrayList();
			usersListView.setItems(names);

			// Start a separate thread to read incoming data from the serial port
			Thread receiveThread = new Thread(this::receiveData);
			receiveThread.setDaemon(true);
			receiveThread.start();
		} else {
			System.out.println("Serial port is not open.");
			// Handle the case when the serial port is not open, e.g., show an error message
		}

		Platform.runLater(() -> {
			// usersListView.setItems(names);
			messageField.requestFocus();
		});

		messageContainer.setSpacing(10);
		messageContainer.setPadding(new Insets(10));

		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);

		ScrollPaneUtils.scrollToBottom(messageContainer, scrollPane);
	}

	private void receiveData() {
        while (serialPort != null && serialPort.isOpen()) {
            try {
                while (serialPort.bytesAvailable() == 0) {
                    Thread.sleep(100);
                }

                byte[] receivedData = new byte[serialPort.bytesAvailable()];
                int numRead = serialPort.readBytes(receivedData, receivedData.length);

                if (numRead > 0) {
                    String receivedMessage = new String(receivedData, 0, numRead);
                    System.out.println("Received message: " + receivedMessage);

                    if (receivedMessage.startsWith("LOGIN:")) {
                        String username = receivedMessage.substring(6).trim();
                        System.out.println("Extracted username: " + username);

                        // Add the username to the user list
                        addUsername(username);

                        // Send confirmation message back to the sender
                        String confirmationMessage = "LOGIN_CONFIRMED";
                        sensorWriter.println(confirmationMessage);
                        sensorWriter.flush();
                        System.out.println("Sent confirmation: " + confirmationMessage);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


	private void addUsername(String username) {
		Platform.runLater(() -> {
			ObservableList<String> names = usersListView.getItems();
			if (!names.contains(username)) {
				names.add(username);
			}
		});
	}

	@FXML
	private void handleSendButton() {
		Random random = new Random();
		String message = messageField.getText();
		if (!message.isEmpty()) {
			receiveMessage(messageField.getText(), random.nextBoolean());
			messageField.clear();
		}
	}

	private void receiveMessage(String messageText, boolean isCurrentUser) {
		HBox messageBox = new HBox();
		messageBox.setAlignment(isCurrentUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

		Label messageLabel = new Label(messageText);
		messageLabel.setWrapText(true);
		messageLabel.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 5px;");

		messageBox.getChildren().add(messageLabel);
		messageContainer.getChildren().add(messageBox);
	}

	private static void senden(PrintWriter sensorWriter) throws InterruptedException {
		int numMessages = 600;
		String message = "Forcing Valentyn Data";
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < numMessages; ++i) {
			String data = message + " " + i + "\n";
			System.out.println("SENDING: " + data);
			sensorWriter.println(data);
			sensorWriter.flush();
		}

		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		double elapsedSeconds = elapsedTime / 1000.0;

		int totalBytes = 0;
		for (int i = 0; i < numMessages; ++i) {
			String data = message + " " + i + "\n";
			totalBytes += data.getBytes().length;
		}

		double dataRate = totalBytes / elapsedSeconds;

		System.out.println("Sent " + numMessages + " messages in " + elapsedSeconds + " seconds.");
		System.out.println("Total bytes sent: " + totalBytes);
		System.out.println("Data rate: " + dataRate + " bytes/second");
	}

	private static void empfangen(Scanner sensorReader) {
		try {
			for (int j = 0; j < 1000; ++j) {
				// System.out.println("Read line " + j);
				System.out.println(sensorReader.nextLine());
			}
			sensorReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void switchToPrimary() throws IOException {
		// ViewNavigator.setRoot("primary");
	}
}