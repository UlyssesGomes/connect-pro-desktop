package com.connectpro.connectproserver.utils;

import java.awt.*;

public class MathUtils {
    public static double caculateMagnitude(double x, double y) {
        x = Math.abs(x);
        y = Math.abs(y);

        return Math.sqrt(x * x + y * y);
    }

    public static double getAngleInRadians(float x, float y) {
        return Math.atan2(y, x);
    }

    public static double calculateX(double angleInRadians, int magnitude) {
        return (magnitude * Math.cos(angleInRadians));
    }

    public static double calculateY(double angleInRadians, int magnitude) {
        return (magnitude * Math.sin(angleInRadians));
    }
}
