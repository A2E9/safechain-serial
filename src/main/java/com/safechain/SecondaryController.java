package com.safechain;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

import javafx.fxml.FXML;

public class SecondaryController {


    	public void initialize() throws IOException, InterruptedException{
		Scanner sc = new Scanner(System.in);
		
		// Port auswählen
		System.out.println("Verfügbare Serielle Ports:");
		for (int i = 0; i < SerialPort.getCommPorts().length; i++) {
			System.out.println(i + ": " + SerialPort.getCommPorts()[i].toString());
		}
		;
		System.out.println("Bitte wählen Sie einen Port (0-" + (SerialPort.getCommPorts().length - 1) + "): ");
		int portIndex = sc.nextInt();
		
		// Port öffnen
		SerialPort comPort = SerialPort.getCommPorts()[portIndex];
		comPort.openPort();
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		Scanner sensorReader = new Scanner(comPort.getInputStream());
		PrintWriter sensorWriter = new PrintWriter(comPort.getOutputStream());

		// comPort.setBaudRate(115200);
		
		// Senden oder Empfangen
		System.out.println("Bitte wählen");
		System.out.println("0) Sender");
		System.out.println("1) Empfänger");
		
		if (sc.nextInt() == 0) {
			senden(sensorWriter);
		} else {
			empfangen(sensorReader);
		}
		comPort.closePort();
		sc.close();
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
				//System.out.println("Read line " + j);
				System.out.println(sensorReader.nextLine());
			}
			sensorReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}