package com.safechain;

import java.io.IOException;
import com.fazecast.jSerialComm.SerialPort;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrimaryController {

	public void initialize(Stage primaryStage) throws IOException, InterruptedException {
		VBox vboxForBtn = new VBox();

		System.out.println("null");

		for (int i = 0; i < SerialPort.getCommPorts().length; i++) {
			System.out.println(i + ": " + SerialPort.getCommPorts()[i].toString());

			Button comBtn = new Button();

			comBtn.setText(SerialPort.getCommPorts()[i].toString());

			primaryStage.getChildren().add(comBtn);
		}
	}

	@FXML
	private void switchToSecondary() throws IOException {
		App.setRoot("secondary");

	}

	// private static void senden(PrintWriter sensorWriter) throws
	// InterruptedException {
	// for (int i = 0; i < 600; ++i) {
	// //System.out.println("Sending Data " + i);
	// System.out.println("SENDING: Forcing Valentyn Data " + i + "\n");
	// sensorWriter.println("Forcing Valentyn Data " + i + "\n");
	// sensorWriter.flush();
	// Thread.sleep(1000);
	// }
	// }

}
