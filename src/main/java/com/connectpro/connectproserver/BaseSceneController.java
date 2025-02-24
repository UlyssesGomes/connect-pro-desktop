package com.connectpro.connectproserver;

import com.connectpro.connectproserver.server.Server;
import com.connectpro.connectproserver.utils.notification.NotificationCustom;
import com.connectpro.connectproserver.utils.notification.NotificationType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

enum MenuButtonEnum {
    HOME,
    PHONE_LIST
}

public class BaseSceneController {

    @FXML
    private BorderPane pane;

    @FXML
    private Button startButton;

    @FXML
    private Button shutdownButton;

    @FXML
    private Button testButton;

    @FXML
    private Button menuHomeButton;

    @FXML
    private Button menuPhoneListButton;

    @FXML
    private Label programVersion;

    private Server server;

    private NotificationCustom notification;

    public void initialize() {
        shutdownButton.setDisable(true);
        server = new Server();

        programVersion.setText("v0.4");

        notification = new NotificationCustom();
    }

    public BorderPane getPane() {
        return pane;
    }

    @FXML
    protected void onStartServerButton() {
        setServerStatus(true);
        server.restart();
        //serverLogTextArea.appendText("Server started at port " + server.PORT + "...\n");
    }

    @FXML
    protected void onShutdownServerButton() {
        //serverLogTextArea.appendText("Shutting down server...\n");
        setServerStatus(false);
        server.cancel();
    }

    @FXML
    private void onTestButton() {
        notification.showNotificationType("Notificatio Title",
                "Success notification message is shown here.",
                NotificationType.CONFIRM);

        notification.setAction(e -> System.out.println("{new} Notification clicked on!"));
    }

    private void setServerStatus(boolean status) {
        if (status) {
            startButton.setDisable(true);
            shutdownButton.setDisable(false);
            //labelStatus.setText("Online");
            //imageStatus.setImage(onlineSatusImage);
        } else {
            startButton.setDisable(false);
            shutdownButton.setDisable(true);
            //labelStatus.setText("Offline");
            //imageStatus.setImage(offlineStatusImage);
        }
    }

    @FXML
    protected void onHomeButton() {
        setSelectMenuButton(MenuButtonEnum.HOME);
    }

    @FXML
    protected void onPhoneListButton() {
        setSelectMenuButton(MenuButtonEnum.PHONE_LIST);
    }

    private void setSelectMenuButton(MenuButtonEnum button) {
        if(button == MenuButtonEnum.HOME) {
            System.out.println("Home selected.");
        }
        else if(button == MenuButtonEnum.PHONE_LIST) {
            System.out.println("Phone selected.");
        }
    }
}
