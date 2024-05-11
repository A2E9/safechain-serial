package com.safechain.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ViewNavigator {
    private static Scene scene;
    private static Object data;

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
        FXMLLoader fxmlLoader = new FXMLLoader(ViewNavigator.class.getResource("/com/safechain/" + fxml + ".fxml"));

        return fxmlLoader.load();
    }

    public static void setData(Object data) {
        ViewNavigator.data = data;
    }

    public static Object getData() {
        return data;
    }
}
