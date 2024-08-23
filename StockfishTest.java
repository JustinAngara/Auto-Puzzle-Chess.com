package com.chepuz.main;

import java.awt.AWTException;
import java.io.IOException;

public class StockfishTest {
	static Stockfish client;
	static ExtractSquares sr;
	private static boolean isWhite;
	public static String stockfishHandler(String FEN) throws IOException {

		isWhite = FEN.indexOf("w")!=-1;
		// initialize and connect to engine
		if (client.startEngine()) {
			System.out.println("Engine has started.. in the test file");
		} else {
			System.out.println("Oops! Something went wrong..");
		}

		// send commands manually
		client.sendCommand("uci");

		// receive output dump
		client.getOutput(0);
		
		// do not forget to close this
		//		client.stopEngine();
		
		String bestMove = client.getBestMove(FEN, 100);
		System.out.println("best move: "+bestMove);
		
		// perform calculation
		
		
		return bestMove;
	}
	
	/*
	 return an 2d array
	 [
		{x,y}, 
		{x2,y2}
	 ]
	 * */
	
	public static int letterX(String move) {
		// each tile is 158 pixels
		// we multiply by the number it is located at + 1
		String files = "abcdefgh";
		
		if(!isWhite) {// reverses if black since board is rotated
			files = new StringBuffer(files).reverse().toString(); 
		}
		int temp = files.indexOf(move.substring(0,1));
		temp = (temp*158) + 79;
		return temp;
	}
	
	public static int numberY(String move) {
		String files = "87654321";
		
		if(!isWhite) {// reverses if black since board is rotated
			files = new StringBuffer(files).reverse().toString(); 
		}
		int temp = files.indexOf(move.substring(1,2));
		temp = (temp*158) + 79;
		return temp;
	} 
	
	public static int[][] manageXY(String bestMove) {
		
		String[] moveArr = {bestMove.substring(0,2), bestMove.substring(2,4)};
		System.out.println("MANAGE XY: "+moveArr[0]+", "+moveArr[1]);
		
		int x1 = letterX(moveArr[0]);
		int y1 = numberY(moveArr[0]);
		
		int x2 = letterX(moveArr[1]);
		int y2 = numberY(moveArr[1]);
		return new int [][]{ {x1,y1}, {x2,y2} };
	}

	public static int[][] run() throws IOException, AWTException {
		client = new Stockfish();
		sr = new ExtractSquares();
		System.out.println(sr.retrieveFen());
		try {
			return manageXY(stockfishHandler(sr.retrieveFen()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new int [][]{{0,0},{0,0}};
		
		
	}
	public static void main(String[] args) throws AWTException  {
		
	}
}