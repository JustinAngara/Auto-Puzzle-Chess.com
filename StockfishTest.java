package com.chepuz.main;

import java.awt.AWTException;
import java.io.IOException;

public class StockfishTest {
	static Stockfish client;
	
	public static String stockfishHandler(String FEN) throws IOException {
		

		// initialize and connect to engine
		
		if (!client.startEngine()) {
			return null;
		}

		// send commands manually
		client.sendCommand("uci");

		
		client.getOutput(0);
		
		System.out.println("Best move : " + client.getBestMove(FEN, 100));
		
// do not forget to close this
//		client.stopEngine();
		return client.getBestMove(FEN, 100);
	}
	public static void run() throws AWTException {
		client = new Stockfish();
		ExtractSquares sr = new ExtractSquares();
		try {
			stockfishHandler(sr.retrieveFen());
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws AWTException  {
//		run();
		
	}
}