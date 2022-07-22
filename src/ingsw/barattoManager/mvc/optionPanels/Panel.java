package ingsw.barattoManager.mvc.optionPanels;

import javax.swing.*;
import java.awt.*;

public class Panel {
	public void showErrorMessage(Component parentComponent, String errorMessage) {
		JOptionPane.showMessageDialog(parentComponent, errorMessage, "Errore", JOptionPane.ERROR_MESSAGE);
	}

	public void showSuccesMessage(Component parentComponent, String successMessage, String title) {
		JOptionPane.showMessageDialog(parentComponent, successMessage, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
