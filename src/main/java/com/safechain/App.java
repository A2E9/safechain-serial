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
    @SuppressWarnings("unused")
    private static String serialDevice;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = ViewNavigator.loadFXML("primary");

        Scene scene = new Scene(root);

        ViewNavigator.setScene(scene);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            serialDevice = args[0];
        }
        launch(args);
    }

}

// Compile
// javac --module-path "C:\Users\I0\Downloads\openjfx-22.0.1_windows-x64_bin-sdk\javafx-sdk-22.0.1\lib;src/lib/jSerialComm-2.10.4.jar" --add-modules javafx.controls,javafx.fxml -d target/classes src/main/java/com/safechain/App.java src/main/java/module-info.java src/main/java/com/safechain/*.java

// Run with args
// java --module-path "C:\Users\I0\Downloads\openjfx-22.0.1_windows-x64_bin-sdk\javafx-sdk-22.0.1\lib;src/lib/jSerialComm-2.10.4.jar;target/classes" --add-modules javafx.controls,javafx.fxml -m com.safechain/com.safechain.App COM3