package com.safechain.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class SecondaryController {
	@FXML
	private VBox vboxForBtn;

	@FXML
	private ListView<String> usersListView;

	public void initialize() throws IOException, InterruptedException {

		ObservableList<String> names = FXCollections.observableArrayList(
				"Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");

		Platform.runLater(() -> {
			usersListView.setItems(names);
		});

		// SerialPort comPort = (SerialPort) ViewNavigator.getData();

		// Scanner sc = new Scanner(System.in);

		// Scanner sensorReader = new Scanner(comPort.getInputStream());
		// PrintWriter sensorWriter = new PrintWriter(comPort.getOutputStream());

		// System.out.println("Bitte wählen");
		// System.out.println("0) Sender");
		// System.out.println("1) Empfänger");

		// if (sc.nextInt() == 0) {
		// senden(sensorWriter);
		// } else {
		// empfangen(sensorReader);
		// }
		// comPort.closePort();
		// sc.close();
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