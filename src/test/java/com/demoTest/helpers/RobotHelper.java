package com.demoTest.helpers;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

public class RobotHelper {
    private final Robot robot;

    public RobotHelper() throws AWTException {
        robot = new Robot();
    }

    public void dragAndDrop(int xFrom, int yFrom, int xTo, int yTo) {
        robot.mouseMove(xFrom, yFrom);
        sleep(2);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        sleep(2);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        sleep(2);
        robot.mouseMove(xTo, yTo);
        sleep(2);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void dragAndDropElement(int xFrom, int yFrom, int xTo) {
        robot.mouseMove(xFrom, yFrom);
        sleep(2000);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        sleep(2000);
        robot.mouseMove(xTo, yFrom);
        sleep(2000);
        robot.mouseMove(xTo + 5, yFrom);
        sleep(2000);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void sleep(int seconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(seconds);
        } catch (InterruptedException ignored) {
        }
    }
}
