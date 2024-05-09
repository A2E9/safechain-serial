module com.safechain {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.safechain to javafx.fxml;
    exports com.safechain;
    requires transitive javafx.graphics;

    requires com.fazecast.jSerialComm;
}
