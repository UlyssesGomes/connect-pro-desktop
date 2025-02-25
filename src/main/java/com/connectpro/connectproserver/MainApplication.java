package com.connectpro.connectproserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private Stage primaryStage;
    private BorderPane basePane;

    private BaseSceneController baseSceneController;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("ConnectPro Server");
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing program...");
            System.exit(0);
        });

        initBaseLayout();
        initServerLayout();
        initPhoneListLayout();

        baseSceneController.setLayout(MenuButtonEnum.SERVER_HOME);
    }


    /**
     * Initializes the base layout.
     */
    public void initBaseLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("base-view.fxml"));
            basePane = (BorderPane) loader.load();
            baseSceneController = (BaseSceneController) loader.getController();

            // Show the scene containing the root layout.
            Scene scene = new Scene(basePane);
            scene.getStylesheets().add(0, getClass().getResource("/style/notification.css").toString());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initServerLayout() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("server-view.fxml"));
        try {
            AnchorPane serverPane = (AnchorPane) fxmlLoader.load();
            ServerSceneController serverSceneController = fxmlLoader.getController();
            baseSceneController.addPaneController(MenuButtonEnum.SERVER_HOME, serverSceneController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initPhoneListLayout() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("phone-list-view.fxml"));
        try {
            AnchorPane serverPane = (AnchorPane) fxmlLoader.load();
            PhoneListSceneController phoneListSceneController = fxmlLoader.getController();
            baseSceneController.addPaneController(MenuButtonEnum.PHONE_LIST, phoneListSceneController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}