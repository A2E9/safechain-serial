package com.safechain;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.safechain.utils.ViewNavigator;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = ViewNavigator.loadFXML("primary");

        Scene scene = new Scene(root);

        ViewNavigator.setScene(scene);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}