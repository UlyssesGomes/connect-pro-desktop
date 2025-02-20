module com.connectpro.connectproserver {
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.connectpro.connectproserver to javafx.fxml;
    exports com.connectpro.connectproserver;
}