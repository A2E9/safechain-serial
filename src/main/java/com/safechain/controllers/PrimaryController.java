package com.safechain.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import com.fazecast.jSerialComm.SerialPort;
import com.safechain.utils.ViewNavigator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PrimaryController {
	private SerialPort serialPort;
	private PrintWriter sensorWriter;
	@FXML
	private VBox vboxForBtn;

	@FXML
	private TextField usernameField;

	public void initialize() {

		for (int i = 0; i < SerialPort.getCommPorts().length; i++) {
			System.out.println(i + ": " + SerialPort.getCommPorts()[i].toString());

			Button comBtn = new Button();
			comBtn.setText(SerialPort.getCommPorts()[i].toString());

			final int portIndex = i;
			comBtn.setOnAction(event -> {
				serialPort = SerialPort.getCommPorts()[portIndex];
				serialPort.openPort();
				serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
				switchToSecondary();
			});
			sensorWriter = new PrintWriter(serialPort.getOutputStream());
			vboxForBtn.getChildren().add(comBtn);
		}
	}

	@FXML
	private void switchToSecondary() {
		ViewNavigator.setData(serialPort, sensorWriter);

		try {
			// Send username to establish presence
			String username = usernameField.getText();
			sendUsername(username);

			ViewNavigator.setRoot("secondary", 620, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendUsername(String username) {
        if (serialPort != null && serialPort.isOpen()) {
            String message = "LOGIN:" + username;
            sensorWriter.println(message);
            sensorWriter.flush();
            System.out.println("Sent: " + message);
        }
    }

}
