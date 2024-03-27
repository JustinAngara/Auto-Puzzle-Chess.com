package com.chepuz.main;

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
		
		
//		client.stopEngine();
		return client.getBestMove(FEN, 100);
	}
	
	public static void main(String[] args)  {
		client = new Stockfish();
		try {
			stockfishHandler("8/8/8/4p1K1/4P3/3k4/8/8 w");
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
	}
}