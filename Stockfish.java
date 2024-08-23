package com.chepuz.main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Stockfish {

	private static Process engineProcess;
	private static BufferedReader processReader;
	private static OutputStreamWriter processWriter;

	private static final String PATH = "C:\\Users\\justi\\eclipse-workspace\\JustinProject\\src\\com\\chepuz\\main\\Stockfish\\stockfish\\stockfish-windows-x86-64.exe";

	public boolean startEngine() {
		try {
			engineProcess = Runtime.getRuntime().exec(PATH);
			processReader = new BufferedReader(new InputStreamReader(
					engineProcess.getInputStream()));
			processWriter = new OutputStreamWriter(
					engineProcess.getOutputStream());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Takes in any valid UCI command and executes it
	 * 
	 * @param command
	 */
	public void sendCommand(String command) {
		try {
			processWriter.write(command + "\n");
			processWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is generally called right after 'sendCommand' for getting the raw
	 * output from Stockfish
	 * 
	 * @param waitTime
	 *            Time in milliseconds for which the function waits before
	 *            reading the output. Useful when a long running command is
	 *            executed
	 * @return Raw output from Stockfish
	 */
	public String getOutput(int waitTime) {
		StringBuffer buffer = new StringBuffer();
		try {
			Thread.sleep(waitTime);
			sendCommand("isready");
			while (true) {
				String text = processReader.readLine();
				if (text.equals("readyok"))
					break;
				else
					buffer.append(text + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * This function returns the best move for a given position after
	 * calculating for 'waitTime' ms
	 * 
	 * @param fen
	 *            Position string
	 * @param waitTime
	 *            in milliseconds
	 * @return Best Move in PGN format
	 */
	public String getBestMove(String fen, int waitTime) {
		waitTime=500;
		try {
			sendCommand("position fen " + fen+"\ngo movetime "+ waitTime);

			System.out.println("position fen " + fen);
			System.out.println("go movetime " + waitTime);
			String output = getOutput(500);
			
//			System.out.println(output);
			String[] outputArr = output.split(" ");

			if(output.indexOf("bestmove")==-1){

				int indexOfBestMove = firstCon(outputArr);
				output = outputArr[indexOfBestMove];
			} else{
				
				System.out.println("This is the second option of condition");
				System.out.println(output);
				
				int indexOfBestMove = arrayGetIndex(outputArr);
				output = outputArr[indexOfBestMove];
			}
			System.out.println("This this output: "+output);
			
			return output;
		}catch(Exception e) {
			
		}
		return null;
	}
	public static int firstCon(String[]arr){
		System.out.println("this is arr");
		for(int i = arr.length-1; i>=0;i--){
//			System.out.println(arr[i]);
			if(arr[i].indexOf("pv")!=-1){
				System.out.println("FOUND: "+ arr[i+1]);
				return i+1;
			}

		}
		return -1;
	}
	public static int arrayGetIndex(String[]arr){
		System.out.println("this is arr");
		for(int i = 0; i<arr.length;i++){
//			System.out.println(arr[i]);
			if(arr[i].indexOf("bestmove")!=-1){

				return i+1;
			}

		}
		return -1;
	}
	/**
	 * Stops Stockfish and cleans up before closing it
	 */
	public void stopEngine() {
		try {
			sendCommand("quit");
			processReader.close();
			processWriter.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Get a list of all legal moves from the given position
	 * 
	 * @param fen
	 *            Position string
	 * @return String of moves
	 */
	public String getLegalMoves(String fen) {
		sendCommand("position fen " + fen);
		sendCommand("d");
		return getOutput(0).split("Legal moves: ")[1];
	}

	/**
	 * Draws the current state of the chess board
	 * 
	 * @param fen
	 *            Position string
	 */
	public void drawBoard(String fen) {
		sendCommand("position fen " + fen);
		sendCommand("d");

		String[] rows = getOutput(0).split("\n");

		for (int i = 1; i < 18; i++) {
			System.out.println(rows[i]);
		}
	}

	/**
	 * Get the evaluation score of a given board position
	 * @param fen Position string
	 * @param waitTime in milliseconds
	 * @return evalScore
	 */
	public float getEvalScore(String fen, int waitTime) {
		sendCommand("position fen " + fen);
		sendCommand("go movetime " + waitTime);

		float evalScore = 0.0f;
		String[] dump = getOutput(waitTime + 20).split("\n");
		for (int i = dump.length - 1; i >= 0; i--) {
			if (dump[i].startsWith("info depth ")) {
				try {
				evalScore = Float.parseFloat(dump[i].split("score cp ")[1]
						.split(" nodes")[0]);
				} catch(Exception e) {
					evalScore = Float.parseFloat(dump[i].split("score cp ")[1]
							.split(" upperbound nodes")[0]);
				}
			}
		}
		return evalScore/100;
	}
}