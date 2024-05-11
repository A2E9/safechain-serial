package com.safechain.controllers;

import java.io.IOException;
import com.fazecast.jSerialComm.SerialPort;
import com.safechain.utils.ViewNavigator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PrimaryController {
	private SerialPort comPort;
	@FXML
	private VBox vboxForBtn;

	public void initialize() {

		for (int i = 0; i < SerialPort.getCommPorts().length; i++) {
			System.out.println(i + ": " + SerialPort.getCommPorts()[i].toString());

			Button comBtn = new Button();
			comBtn.setText(SerialPort.getCommPorts()[i].toString());

			final int portIndex = i;
			comBtn.setOnAction(event -> {
				comPort = SerialPort.getCommPorts()[portIndex];
				comPort.openPort();
				comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
				switchToSecondary();
			});

			vboxForBtn.getChildren().add(comBtn);
		}
	}

	@FXML
	private void switchToSecondary() {
		ViewNavigator.setData(comPort);
		try {
			ViewNavigator.setRoot("secondary", 620, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
