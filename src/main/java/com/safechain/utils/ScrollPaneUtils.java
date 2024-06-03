package com.safechain.utils;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ScrollPaneUtils {
    public static void scrollToBottom(VBox container, ScrollPane scrollPane) {
        container.heightProperty().addListener((observable, oldValue, newValue) -> {
            scrollPane.setVvalue(1.0);
        });
    }

}
