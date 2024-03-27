package com.chepuz.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class Screenshot {
    public BufferedImage capture(int x1, int y1, int x2, int y2) throws AWTException {
        Robot robot = new Robot();
        Rectangle screenRectangle = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        BufferedImage capturedImage = robot.createScreenCapture(screenRectangle);
        return capturedImage;
    }
    public static BufferedImage convertToGrayscale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color myRed = new Color(255, 0, 0);
        System.out.println("Width: "+width+", Height: "+height);
        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                int grayscaleValue = (red + green + blue) / 3;
                
                grayscaleImage.setRGB(x, y, grayscaleValue << 16 | grayscaleValue << 8 | grayscaleValue);
                if(x%158==0 || y%158==0) {
                	grayscaleImage.setRGB(x, y, myRed.getRGB());
                }

                
            }
        }

        return grayscaleImage;
    }
}
