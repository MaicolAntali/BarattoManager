package com.barattoManager;

import com.barattoManager.ui.BarattoManagerGui;

import java.awt.*;

/**
 * Main Class
 */
public class BarattoManager {
	/**
	 * Main method
	 *
	 * @param args NO ARGS ACCEPTED
	 */
	public static void main(String[] args) {
		new Thread(() -> EventQueue.invokeLater(
				() -> new BarattoManagerGui().setVisible(true))
		).start();
	}
}
