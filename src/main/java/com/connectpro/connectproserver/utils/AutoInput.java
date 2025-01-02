package com.connectpro.connectproserver.utils;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoInput {

    private Robot robot;
    private final int STEP_SMOOTH_MOVE = 2;

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
}
