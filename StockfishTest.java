package com.chepuz.main;

import java.awt.AWTException;
import java.io.IOException;

public class StockfishTest {
	static Stockfish client;
	static ExtractSquares sr;
	
	public static String stockfishHandler(String FEN) throws IOException {
		// initialize and connect to engine
		if (client.startEngine()) {
			System.out.println("Engine has started..");
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
		

		return bestMove;
	}
	public static void run() throws IOException, AWTException {
		client = new Stockfish();
		sr = new ExtractSquares();
		System.out.println(sr.retrieveFen());
		try {
			stockfishHandler(sr.retrieveFen());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws AWTException  {
		
	}
}