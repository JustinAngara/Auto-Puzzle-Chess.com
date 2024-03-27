package com.chepuz.main;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class MouseMoverHelper implements NativeKeyListener {

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()==NativeKeyEvent.VC_O) {
			System.out.println("test");
			moveX(-1,0);
		}
		if(arg0.getKeyCode()==NativeKeyEvent.VC_P) {
			System.out.println("tyest 2");
			moveX(1,0);
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void moveX(int x, int y) {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x1 = (int) b.getX();
		int y1 = (int) b.getY();
		try {
		    // These coordinates are screen coordinates


		    // Move the cursor
		    Robot robot = new Robot();
		    robot.mouseMove(x1+x, y1+y);
		} catch (AWTException e) {
		}
	}
	public static void main(String[] args) {
		GlobalScreen.addNativeKeyListener(new MouseMoverHelper());
		LogManager.getLogManager().reset();

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
		
		}
	}
}
