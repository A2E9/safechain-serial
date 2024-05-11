module com.safechain {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.safechain;

    opens com.safechain to javafx.fxml;
    opens com.safechain.controllers to javafx.fxml;

    requires transitive javafx.graphics;

    requires com.fazecast.jSerialComm;
}
