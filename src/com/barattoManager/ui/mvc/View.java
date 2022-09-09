package com.barattoManager.ui.mvc;

import javax.swing.*;

/**
 * Interface that represent an MVC view <br>
 * It contains a single method common to all the View
 * {@link #getMainJPanel()} which returns the panel of the user interface
 */
public interface View {

	/**
	 * Method used to get a {@link JPanel}
	 * @return {@link JPanel}
	 */
	JPanel getMainJPanel();

}
