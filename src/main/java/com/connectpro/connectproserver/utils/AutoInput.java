package com.connectpro.connectproserver.utils;

import com.connectpro.connectproserver.server.ConnectionMessageProtocol;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class AutoInput {

    private Robot robot;
    private final int STEP_SMOOTH_MOVE = 2;

    private int finalButton = -1;

    public AutoInput() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.err.println("Error while try to instantiate robot." + e.getMessage());
        }
    }

    public void smoothMouseMove(int x, int y) {
        PointerInfo pointer = MouseInfo.getPointerInfo();

        double magnitude = MathUtils.caculateMagnitude(x, y);

        if(magnitude >= STEP_SMOOTH_MOVE) {
            double angleInRad = MathUtils.getAngleInRadians(x, y);
            for(int u = 1; u <= magnitude; u += STEP_SMOOTH_MOVE) {
                double newX = MathUtils.calculateX(angleInRad, u);
                double newY = MathUtils.calculateY(angleInRad, u);

                robot.mouseMove((int)(pointer.getLocation().x + newX), (int)(pointer.getLocation().y + newY));
                try {
                    Thread.sleep(0, 250000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            robot.mouseMove(pointer.getLocation().x + x, pointer.getLocation().y + y);
        }
    }

    public void mouseLeftClick() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void keyDown(String character) {
        getKeyCommand(character, ConnectionMessageProtocol.KEY_DOWN);
    }

    public void keyUp(String character) {
        getKeyCommand(character, ConnectionMessageProtocol.KEY_UP);
    }
    
    private void getKeyCommand(String key, String command) {
        int keycode = -1;
        switch(key.toLowerCase()) {
            case "0":
                keycode = KeyEvent.VK_0;
                break;
            case "1":
                keycode = KeyEvent.VK_1;
                break;
            case "2":
                keycode = KeyEvent.VK_2;
                break;
            case "3":
                keycode = KeyEvent.VK_3;
                break;
            case "4":
                keycode = KeyEvent.VK_4;
                break;
            case "5":
                keycode = KeyEvent.VK_5;
                break;
            case "6":
                keycode = KeyEvent.VK_6;
                break;
            case "7":
                keycode = KeyEvent.VK_7;
                break;
            case "8":
                keycode = KeyEvent.VK_8;
                break;
            case "9":
                keycode = KeyEvent.VK_9;
                break;
            case "a":
                keycode = KeyEvent.VK_A;
                break;
            case "b":
                keycode = KeyEvent.VK_B;
                break;
            case "c":
                keycode = KeyEvent.VK_C;
                break;
            case "d":
                keycode = KeyEvent.VK_D;
                break;
            case "e":
                keycode = KeyEvent.VK_E;
                break;
            case "f":
                keycode = KeyEvent.VK_F;
                break;
            case "g":
                keycode = KeyEvent.VK_G;
                break;
            case "h":
                keycode = KeyEvent.VK_H;
                break;
            case "i":
                keycode = KeyEvent.VK_I;
                break;
            case "j":
                keycode = KeyEvent.VK_J;
                break;
            case "k":
                keycode = KeyEvent.VK_K;
                break;
            case "l":
                keycode = KeyEvent.VK_L;
                break;
            case "m":
                keycode = KeyEvent.VK_M;
                break;
            case "n":
                keycode = KeyEvent.VK_N;
                break;
            case "o":
                keycode = KeyEvent.VK_O;
                break;
            case "p":
                keycode = KeyEvent.VK_P;
                break;
            case "q":
                keycode = KeyEvent.VK_Q;
                break;
            case "r":
                keycode = KeyEvent.VK_R;
                break;
            case "s":
                keycode = KeyEvent.VK_S;
                break;
            case "t":
                keycode = KeyEvent.VK_T;
                break;
            case "u":
                keycode = KeyEvent.VK_U;
                break;
            case "v":
                keycode = KeyEvent.VK_V;
                break;
            case "w":
                keycode = KeyEvent.VK_W;
                break;
            case "x":
                keycode = KeyEvent.VK_X;
                break;
            case "y":
                keycode = KeyEvent.VK_Y;
                break;
            case "z":
                keycode = KeyEvent.VK_Z;
                break;
            case "shift":
                keycode = KeyEvent.VK_SHIFT;
                break;
            case "enter":
                keycode = KeyEvent.VK_ENTER;
                break;
            case "space":
                keycode = KeyEvent.VK_SPACE;
                break;
            case "bckspc":
            	keycode = KeyEvent.VK_BACK_SPACE;
                break;
            case ",":
                keycode = KeyEvent.VK_COMMA;
                break;
            case ".":
                keycode = KeyEvent.VK_PERIOD;
                break;
            case "@":
                keycode = KeyEvent.VK_2;
                finalButton = KeyEvent.VK_AT;
                robot.keyPress(KeyEvent.VK_SHIFT);
                break;
            case "/":
                keycode = KeyEvent.VK_SLASH;
                break;
        }

        if(command.equals(ConnectionMessageProtocol.KEY_UP) && keycode != -1) {
            robot.keyRelease(keycode);
            if(finalButton == KeyEvent.VK_AT) {
                finalButton = -1;
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        } else if (command.equals(ConnectionMessageProtocol.KEY_DOWN) && keycode != -1){
    	    robot.keyPress(keycode);
        } else if ( keycode == -1) {
            System.out.println("\"" + key + " \" is not a valid key");
        }
    }
}
