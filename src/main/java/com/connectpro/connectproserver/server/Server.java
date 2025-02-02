package com.connectpro.connectproserver.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server extends Service<Void> {
    public final int PORT = 7171;
    public String IP;
    private boolean isRunning;

    private ServerSocket serverSocket;

    private ArrayList<WorkerConnection> connectionList;

    public Server() {
        try {
            IP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            IP = "-";
            throw new RuntimeException(e);
        }
    }

    private void startServer() {
        isRunning = true;
        connectionList = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            System.err.println("Error starting server");
        }
    }

    private void closeServer() {
        try {
            isRunning = false;
            if(!serverSocket.isClosed())
                serverSocket.close();

        } catch (IOException e) {
            System.err.println("Error closing server : " + e.getMessage());
        }
        closeAllSockets();
    }

    private void closeAllSockets() {
        for (WorkerConnection connection : connectionList) {
            try {
                connection.shutDownCommunication();
            } catch (IOException e) {
                System.err.println("Error while closing active socket. " + e.getMessage());
            }
        }
    }

    private void serverRun() {
        startServer();

        WorkerConnection worker;
        while(isRunning) {
            try {
                Socket socket = serverSocket.accept();

                worker = new WorkerConnection(socket);
                connectionList.add(worker);
                worker.start();

            } catch (IOException e) {
                System.err.println("An error occurred while accepting connections: " + e.getMessage());
            }
        }

        closeServer();
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        closeServer();
    }

    @Override
    protected void failed() {
        super.failed();
        closeServer();
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override protected Void call() throws InterruptedException {
                serverRun();
                return null;
            }
        };
    }

    public String getIp() {
        return IP;
    }
}
