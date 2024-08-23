package com.chepuz.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
/*
 * possible apis 
 * https://www.chessdb.cn/cdb.php?action=querybest&board=rnbqkbnr/pppp1ppp/4p3/8/4P3/8/PPPP1PPP/RNBQKBNR%20b%20KQkq%20-%200%202
 * https://www.chessdb.cn/cdb.php?action=querybest&board=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR%20w%20KQkq%20-%200%201
 * https://chess-api.com/
 * 
 * */

public class Main implements NativeKeyListener{

	private static JFrame frame = new JFrame();


	private static ImageIcon icon;
	private static Timer t; 
	private static JLabel lblNewLabel = new JLabel("");
	private static int x1,x2,y1,y2;

	static StockfishTest sft;
	/**
	 * Launch the application.
	 * @throws AWTException 
	 */
	public static void main(String[] args) throws AWTException {





        sft = new StockfishTest();
		GlobalScreen.addNativeKeyListener(new Main());
		LogManager.getLogManager().reset();

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
		
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws AWTException
	 */
	public Main() throws AWTException {
		x1 = 241;
		y1 = 144;
		x2 = 1506;
		y2 = 1409;


		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws AWTException 
	 */
	private void initialize() throws AWTException {

		
		
		frame.setBounds(x1, y1, x2-x1, y2-y1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);
		lblNewLabel.setBounds(0, 0, x2-x1, y2-y1);
		frame.getContentPane().add(lblNewLabel);

		
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setFocusable(false);
		
	}
	
	
	public ImageIcon returnImage(int[][]xyCoords) throws IOException {
		System.out.println("on");
		Screenshot screenshot = new Screenshot();
		try {
		    BufferedImage capturedImage = screenshot.capture(x1, y1, x2, y2);
		    capturedImage = Screenshot.convertToGrayscale(capturedImage, xyCoords);
		    ImageIcon t = new ImageIcon(capturedImage);
		    return t;
		} catch (AWTException e1) {
		    e1.printStackTrace();
		}
		return null;

	}
	public static void sleep() {
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()==NativeKeyEvent.VC_C) {
			
			try {
				 
				lblNewLabel.setIcon(returnImage(sft.run()));
				frame.setVisible(true);
				Thread.sleep(300);
				frame.setVisible(false);
			} catch (AWTException | IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void changeTimerDir() {
		if(t.isRunning()) {
			t.stop();
		} else {
			t.start();
		}
	}

	
}
