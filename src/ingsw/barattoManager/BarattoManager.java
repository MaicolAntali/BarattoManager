package ingsw.barattoManager;

import ingsw.barattoManager.mvc.application.main.ApplicationController;
import ingsw.barattoManager.mvc.application.main.ApplicationUi;

import java.awt.*;

/**
 * Main Class
 */
public class BarattoManager {
	/**
	 * Main method
	 *
	 * @param args no args
	 */
	public static void main(String[] args) {
		new Thread(() -> EventQueue.invokeLater(
				() -> {
					var ui = new ApplicationUi();
					new ApplicationController(ui);
					ui.setVisible(true);
				})
		).start();
	}
}
