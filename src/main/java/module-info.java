module com.connectpro.connectproserver {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.connectpro.connectproserver to javafx.fxml;
    exports com.connectpro.connectproserver;
}