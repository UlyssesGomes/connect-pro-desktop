package com.connectpro.connectproserver;

import com.connectpro.connectproserver.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("server-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600,400);
        stage.setTitle("ConnectPro Server");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            System.out.println("Closing program...");
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}