package com.chepuz.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Screenshot {
	private static Robot bot;
	public Screenshot() throws AWTException {
		bot = new Robot();
	}
    public BufferedImage capture(int x1, int y1, int x2, int y2) throws AWTException {
        Robot robot = new Robot();
        Rectangle screenRectangle = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        BufferedImage capturedImage = robot.createScreenCapture(screenRectangle);

        return capturedImage;
    }
    public static BufferedImage convertToGrayscale(BufferedImage image, int[][] xyCoords) throws IOException, AWTException, InterruptedException {
        
    	image = ImageIO.read(new File("C:\\Users\\justi\\eclipse-workspace\\JustinProject\\image\\invisboard.png"));
    	int width = image.getWidth();
        int height = image.getHeight();
        Color myRed = new Color(255, 0, 0);
        Color myGreen= new Color(0, 255, 0);
//        
//        System.out.println("Width: "+width+", Height: "+height);
//        
//        // absolute xy is x+241, y+144
//        
//        
//        move(xyCoords[0][0] + Main.x1, xyCoords[0][1] + Main.y1);
//        move(xyCoords[1][0] + Main.x1, xyCoords[1][1] + Main.y1);
//        Thread.sleep(50);
//        click();
//        
//        for(int i = 0; i<2;i++) {
//        
//	        for(int w = -20; w<20;w++) {
//	        	for(int l = -20; l<20;l++) {
//	        		if(i==1) {
//	        			image.setRGB(xyCoords[i][0]+w, xyCoords[i][1]+l, myGreen.getRGB());
//	            	}else {
//	            		image.setRGB(xyCoords[i][0]+w, xyCoords[i][1]+l, myRed.getRGB());
//	            	}
//	        	}
//	        }
//        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(x%158==0 || y%158==0) {
                	image.setRGB(x, y, myRed.getRGB());
                }

                
            }
        }
        return image;
    }
    
    public static void move(int x, int y) throws AWTException, InterruptedException{
        
        bot.mouseMove(x, y);    
        click();
        Thread.sleep(150);
    }
    public static void click() throws InterruptedException {
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(25);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

}
