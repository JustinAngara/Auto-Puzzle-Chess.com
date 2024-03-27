package com.chepuz.main;

import java.io.IOException;

public class StockfishTest {
	static Stockfish client;
	
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
		System.out.println(client.getOutput(0));

		// get the best move for a position with a given think time
		System.out.println("Best move : " + client.getBestMove(FEN, 100));
//		client.stopEngine();
		return client.getBestMove(FEN, 100);
	}
	
	public static void main(String[] args)  {
		client = new Stockfish();
		try {
			stockfishHandler("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
	}
}