package com.safechain.utils;

import java.io.PrintWriter;

import com.fazecast.jSerialComm.SerialPort;

public class TransferData {
    private SerialPort serialPort;
    private PrintWriter sensorWriter;

    public TransferData(SerialPort serialPort, PrintWriter sensorWriter) {
        this.serialPort = serialPort;
        this.sensorWriter = sensorWriter;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public PrintWriter getSensorWriter() {
        return sensorWriter;
    }
}