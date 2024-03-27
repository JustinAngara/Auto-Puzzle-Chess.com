package com.chepuz.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ExtractSquares {
	public static Map<Integer, String> pieceMap = new HashMap<>();
	public static Robot bot;
    public static List<BufferedImage> extractSquares(BufferedImage screenshot) throws IOException {
        int squareSize = 158; // Size of each extracted square
   
        List <BufferedImage> bf = new ArrayList<BufferedImage>();
        // Validate input image dimensions to ensure they are divisible by squareSize (optional)
        if (screenshot.getWidth() % squareSize != 0 || screenshot.getHeight() % squareSize != 0) {
            System.out.println("Warning: Image dimensions not perfectly divisible by square size.");
        }

        int count = 0; // Counter for extracted images

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int startX = x * squareSize;
                int startY = y * squareSize;
                int endX = Math.min(startX + squareSize, screenshot.getWidth()); // Handle potential right edge overflow
                int endY = Math.min(startY + squareSize, screenshot.getHeight()); // Handle potential bottom edge overflow

                BufferedImage subImage = screenshot.getSubimage(startX, startY, endX - startX, endY - startY);

                // Save the extracted square image with a unique name (replace with your desired naming convention)
// saves to file loc
//                ImageIO.write(subImage, "PNG", new File("square_" + count + ".png"));
                
                bf.add(subImage);
                
                count++;
            }
        }

        System.out.println("Successfully extracted " + count + " squares.");
		return bf;
    }
    public static int getAverageRGB(BufferedImage bi) {
    	int c= 0;
    	int h = bi.getHeight();
   
    	for(int i =0; i<bi.getWidth(); i++) {
            int rgb = bi.getRGB(i, h/2);
            c += (rgb >> 16) & 0xff;
    	}

    	return c/3;
    }
    
    public static void initPieces() {
        pieceMap.put(8887, "p"); // Light Square
        pieceMap.put(6324, "p"); // Dark Square
        pieceMap.put(8410, "r"); // Light Square
        pieceMap.put(6105, "r"); // Dark Square
        pieceMap.put(8028, "b"); // Light Square
        pieceMap.put(5933, "b"); // Dark Square
        pieceMap.put(6756, "n"); // Light Square
        pieceMap.put(5320, "n"); // Dark Square
        pieceMap.put(6836, "q"); // Light Square
        pieceMap.put(5416, "q"); // Dark Square
        pieceMap.put(7086, "k"); // Light Square
        pieceMap.put(5453, "k"); // Dark Square
        
        pieceMap.put(10763, "P"); // Light Square
        pieceMap.put(8200, "P"); // Dark Square
        pieceMap.put(8802, "R"); // Light Square
        pieceMap.put(11107, "R"); // Dark Square
        pieceMap.put(10890, "B"); // Light Square
        pieceMap.put(8797, "B"); // Dark Square
        pieceMap.put(10952, "N"); // Light Square
        pieceMap.put(9516, "N"); // Dark Square
        pieceMap.put(11378, "Q"); // Light Square
        pieceMap.put(9957, "Q"); // Dark Square
        pieceMap.put(10739, "K"); // Light Square
        pieceMap.put(9104, "K"); // Dark Square
        pieceMap.put(10980, ".");
        pieceMap.put(7316, ".");

    }
    
    public static String[][] getChessboard(List<BufferedImage> squares) {
        String[][] board = new String[8][8];

        // Iterate through extracted squares and match pieces
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int index = y * 8 + x; // Calculate index based on row-major order
                BufferedImage square = squares.get(index);
                String pieceAndColor = matchPiece(getAverageRGB(square));
                board[y][x] = pieceAndColor;
            }
        }
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                System.out.print(board[y][x] + " ");
            }
            System.out.println();
        }
        return board;
    }

    public static String matchPiece(int pieceCode) {

        // Define piece codes and colors (combined light/dark squares)
       
        // Match the piece code and return the piece and color
        String pieceAndColor = pieceMap.get(pieceCode);
        if (pieceAndColor != null) {
            return pieceAndColor;
        } else {
            return "Piece not found";
        }
    }

    public static String getFENNotation(String[][] board, String activeColor) {
        StringBuilder fen = new StringBuilder();

        // Iterate through rows (in correct order this time)
        for (int rank = 0; rank < 8; rank++) {
            int emptyCount = 0;
            for (int file = 0; file < 8; file++) {
                String piece = board[rank][file];
                if (piece.equals(".")) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    fen.append(piece);
                }
            }
            if (emptyCount > 0) {
                fen.append(emptyCount);
            }
            fen.append("/");
        }

        // Remove trailing slash
        fen.deleteCharAt(fen.length() - 1);

        // Add active color and castling rights (assuming no castling for now)
        fen.append(" ").append(activeColor);
        fen.append(" - -");
        fen.append(" 0");

        // Add fullmove number (assuming 1 for now)
        fen.append(" 1");
        System.out.println(fen.toString());
        return fen.toString();
    }
    public static String getTurn() throws AWTException {
    	String s="w";
    	if(grabColor(1800,188)) {
    		s="b";
    	}
    	System.out.println(s);
    	return s;
    }
	public static boolean grabColor(int x, int y) throws AWTException {
		return 75==bot.getPixelColor(x,y).getRed();
	}
	
    public static String retrieveFen() throws IOException, AWTException {
    	int x1,x2,y1,y2;
		x1 = 356;
		y1 = 144;
		x2 = 1621;
		y2 = 1409;
		bot = new Robot();
		initPieces();
		
		Screenshot screenshot = new Screenshot();
	    BufferedImage capturedImage = screenshot.capture(x1, y1, x2, y2);
	    capturedImage = Screenshot.convertToGrayscale(capturedImage);
	    
	    // row major 
        List<BufferedImage> b = extractSquares(capturedImage);

    	return getFENNotation(getChessboard(b), getTurn());
    }
    public static void main(String[] args) throws IOException, AWTException {
    	retrieveFen();
    }
}