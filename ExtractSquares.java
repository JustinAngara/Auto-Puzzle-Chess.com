package com.chepuz.main;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ExtractSquares {

    public static void extractSquares(BufferedImage screenshot) throws IOException {
        int squareSize = 158; // Size of each extracted square
        int numSquaresPerRow = (int) Math.ceil((double) screenshot.getWidth() / squareSize); // Number of squares per row (rounded up)
        int numSquaresPerCol = (int) Math.ceil((double) screenshot.getHeight() / squareSize); // Number of squares per column (rounded up)

        // Validate input image dimensions to ensure they are divisible by squareSize (optional)
        if (screenshot.getWidth() % squareSize != 0 || screenshot.getHeight() % squareSize != 0) {
            System.out.println("Warning: Image dimensions not perfectly divisible by square size.");
        }

        int count = 0; // Counter for extracted images

        for (int y = 0; y < numSquaresPerCol; y++) {
            for (int x = 0; x < numSquaresPerRow; x++) {
                int startX = x * squareSize;
                int startY = y * squareSize;
                int endX = Math.min(startX + squareSize, screenshot.getWidth()); // Handle potential right edge overflow
                int endY = Math.min(startY + squareSize, screenshot.getHeight()); // Handle potential bottom edge overflow

                BufferedImage subImage = screenshot.getSubimage(startX, startY, endX - startX, endY - startY);

                // Save the extracted square image with a unique name (replace with your desired naming convention)
                ImageIO.write(subImage, "PNG", new File("square_" + count + ".png"));

                count++;
            }
        }

        System.out.println("Successfully extracted " + count + " squares.");
    }

    public static void main(String[] args) throws IOException, AWTException {
        // Replace "screenshot.png" with the actual path to your screenshot image
    	int x1,x2,y1,y2;
		x1 = 356;
		y1 = 144;
		x2 = 1621;
		y2 = 1409;
		Screenshot screenshot = new Screenshot();
	    BufferedImage capturedImage = screenshot.capture(x1, y1, x2, y2);
	    capturedImage = Screenshot.convertToGrayscale(capturedImage);
	    
	    
        extractSquares(capturedImage);
    }
}