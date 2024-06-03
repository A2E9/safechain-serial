package com.safechain.utils;

import java.io.IOException;
import java.io.PrintWriter;

import com.fazecast.jSerialComm.SerialPort;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ViewNavigator {
    private static Scene scene;
    private static TransferData data;

    public static void setScene(Scene scene) {
        ViewNavigator.scene = scene;
    }

    public static void setRoot(String fxml, double width, double height) throws IOException {
        Parent root = loadFXML(fxml);
        scene.setRoot(root);
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
    }

    public static Parent loadFXML(String fxml) throws IOException {
        System.out.println("aaaaaa");
        FXMLLoader fxmlLoader = new FXMLLoader(ViewNavigator.class.getResource("/com/safechain/" + fxml + ".fxml"));
        System.out.println(fxmlLoader);
        return fxmlLoader.load();
    }

    public static void setData(SerialPort serialPort, PrintWriter sensorWriter) {
        ViewNavigator.data = new TransferData(serialPort, sensorWriter);
    }

    public static TransferData getData() {
        return data;
    }
}
