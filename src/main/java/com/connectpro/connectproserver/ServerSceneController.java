package com.connectpro.connectproserver;

import com.connectpro.connectproserver.server.Server;
import com.connectpro.connectproserver.utils.WorkerMessageConstants;
import com.connectpro.connectproserver.utils.designpatterns.observable.IObservable;
import com.connectpro.connectproserver.utils.designpatterns.observable.Observer;
import com.connectpro.connectproserver.utils.designpatterns.observable.Subject;
import com.connectpro.connectproserver.utils.observableimplementations.ObservableData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ServerSceneController implements Observer<ObservableData<String>> {

    @FXML
    private Button startButton;

    @FXML
    private Button shutdownButton;

    @FXML
    private TextArea serverLogTextArea;

    @FXML
    private Label labelStatus;

    @FXML
    private Label deviceCountLabel;

    @FXML
    private Label programVersion;

    @FXML
    private ImageView imageStatus;

    private Server server;

    private int deviceCount;

    private Alert a;

    private Image onlineSatusImage;
    private Image offlineStatusImage;

    private List<IObservable<ObservableData<String>>> observableList;

    private static ServerSceneController serverSceneController;

    public void initialize() {
        shutdownButton.setDisable(true);
        server = new Server();
        a = new Alert(Alert.AlertType.NONE);

        offlineStatusImage = new Image(getClass().getResource("/images/internet-icon.png").toString());
        onlineSatusImage = new Image(getClass().getResource("/images/internet-on-icon.png").toString());

        serverSceneController = this;
        observableList = new ArrayList<>();

        deviceCount = 0;
        deviceCountLabel.setText(deviceCount + " Device");

        programVersion.setText("v0.3");
    }

    public void onClose() {
        for(IObservable<ObservableData<String>> o : observableList) {
            o.detach(this);
        }
    }

    @FXML
    protected void onStartServerButton() {
        setServerStatus(true);
        server.restart();
        serverLogTextArea.appendText("Server started at port " + server.PORT + "...\n");
    }

    private void callAlert() {

        // set alert type
        a.setAlertType(Alert.AlertType.INFORMATION);

        // show the dialog
        System.out.println("show alert.");
        a.showAndWait();
    }

    @FXML
    protected void onShutdownServerButton() {
        serverLogTextArea.appendText("Shutting down server...\n");
        setServerStatus(false);
        server.cancel();
    }

    private void setServerStatus(boolean status) {
        if (status) {
            startButton.setDisable(true);
            shutdownButton.setDisable(false);
            labelStatus.setText("Online");
            imageStatus.setImage(onlineSatusImage);
        } else {
            startButton.setDisable(false);
            shutdownButton.setDisable(true);
            labelStatus.setText("Offline");
            imageStatus.setImage(offlineStatusImage);
        }
    }

    @Override
    public synchronized void update(Subject<ObservableData<String>> subjectValue) {
        if (subjectValue.subjectName.equals(WorkerMessageConstants.SUBJECT_NAME)) {
            Platform.runLater(() -> serverLogTextArea.appendText("[Device " + subjectValue.data.id + "] " + subjectValue.data.data + "\n"));
        } else if(subjectValue.subjectName.equals(WorkerMessageConstants.CONNECTION_ADD)) {
            deviceCount++;
            updateDeviceCount();
        } else if(subjectValue.subjectName.equals(WorkerMessageConstants.CONNECTION_REMOVE)) {
            deviceCount--;
            updateDeviceCount();
        }
    }

    public static ServerSceneController getInstance() {
        if(serverSceneController == null) {
            serverSceneController = new ServerSceneController();
        }
        return serverSceneController;
    }

    public void addObservable(IObservable<ObservableData<String>> observable) {
        observableList.add(observable);
    }

    private void updateDeviceCount() {
        Platform.runLater(() -> {
            String deviceMessage;
            if(deviceCount <= 1) {
            	deviceMessage = deviceCount + " Device";
                deviceCountLabel.setText(deviceMessage);
            } else {
                deviceMessage = deviceCount + " Devices";
                deviceCountLabel.setText(deviceMessage);
            }
        });
    }
}