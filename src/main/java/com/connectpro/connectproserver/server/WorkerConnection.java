package com.connectpro.connectproserver.server;

import com.connectpro.connectproserver.ServerSceneController;
import com.connectpro.connectproserver.utils.IdGenerator;
import com.connectpro.connectproserver.utils.WorkerMessageConstants;
import com.connectpro.connectproserver.utils.designpatterns.observable.IObservable;
import com.connectpro.connectproserver.utils.designpatterns.observable.Observer;
import com.connectpro.connectproserver.utils.designpatterns.observable.Subject;
import com.connectpro.connectproserver.utils.observableimplementations.ObservableData;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WorkerConnection extends Thread implements IObservable<ObservableData<String>> {

    private int id;
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private boolean isRunning;
    private Robot robot;

    private int count = 0;

    private List<Observer<ObservableData<String>>> observersList = new ArrayList<>();

    Subject<ObservableData<String>> subject;

    private final String observableName = "WorkerConnection";

    public WorkerConnection(Socket socket) {
        this.id = IdGenerator.generateId();
        this.socket = socket;

        try {
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        isRunning = true;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.err.println("Error while try to instantiate robot." + e.getMessage());
        }

        ServerSceneController scene = ServerSceneController.getInstance();
        if(scene != null) {
            scene.addObservable(this);
            attach(scene);
        }

        subject = new Subject<>(getObservableName(), new ObservableData<String>());
    }

    public void run() {
        printMessage("New Connection established...");
        addDevice();
        while(isRunning) {
            try {
                waitingForData();

                getCommand();

            } catch (Exception e) {
                System.err.println("Connection finished." + e.getMessage());
            }
        }
        printMessage("Thread finished.");
        removeDevice();
    }

    private void getCommand() throws IOException, InterruptedException {
        String command = inputStream.readUTF();

        String[] words = command.split("\\|");
        if(words.length > 0) {
            if(words[0].equals(ConnectionMessageProtocol.MOVE)) {
                PointerInfo pointer = MouseInfo.getPointerInfo();
                robot.mouseMove(pointer.getLocation().x + Integer.parseInt(words[1]), pointer.getLocation().y + Integer.parseInt(words[2]));
                System.out.println("Move command with params: [" + words[1] + ", " + words[2] + "]" + " Count: " + count);
                count++;
            }
            else if(words[0].equals(ConnectionMessageProtocol.CLICK)) {
                mouseLeftClick();
            }
            else if(words[0].equals(ConnectionMessageProtocol.DOUBLE_CLICK)) {
                mouseLeftClick();
                Thread.sleep(30);
                mouseLeftClick();
            }
            else if(words[0].equals(ConnectionMessageProtocol.FINISH_CONNECTION)) {
                printMessage("Connection finished with device.");
                shutDownCommunication();
            }
        }
    }

    private void waitingForData() throws IOException {
        int bytesAvailable;
        do {
            bytesAvailable = inputStream.available();
        } while(bytesAvailable <= 0 && isRunning && !socket.isClosed()) ;
    }

    public void shutDownCommunication() throws IOException {
        isRunning = false;
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    @Override
    public String getObservableName() {
        return observableName;
    }

    @Override
    public void attach(Observer observer) {
        observersList.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observersList.remove(observer);
    }

    @Override
    public void notify(Subject<ObservableData<String>> subjectValue) {
        for(Observer o : observersList){
            o.update(subjectValue);
        }
    }

    public void printMessage(String message) {
        subject.subjectName = WorkerMessageConstants.SUBJECT_NAME;
        subject.data.id = id;
        subject.data.data = message;
        notify(subject);
    }

    public void addDevice() {
        subject.subjectName = WorkerMessageConstants.CONNECTION_ADD;
        subject.data.id = id;
        subject.data.data = "New device add.";
        notify(subject);
    }

    public void removeDevice() {
        subject.subjectName = WorkerMessageConstants.CONNECTION_REMOVE;
        subject.data.id = id;
        subject.data.data = "Device removed.";
        notify(subject);
    }

    private void mouseLeftClick() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    private void smoothMouseMove(int x, int y) {
        PointerInfo pointer = MouseInfo.getPointerInfo();

        float magnitude = caculateMagniture(pointer.getLocation().x - x, pointer.getLocation().y - y));
    }
}
