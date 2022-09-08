package com.barattoManager.ui.action.actions;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.exception.NullObjectException;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.action.Action;
import com.barattoManager.ui.mvc.dialogs.newCategory.NewCategoryController;
import com.barattoManager.ui.mvc.dialogs.newCategory.NewCategoryModel;
import com.barattoManager.ui.mvc.dialogs.newCategory.NewCategoryView;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;

/**
 * Action used to add a root category
 */
public class AddRootCategoryAction implements Action {

	private static final String TITLE_CREATION_OF_A_ROOT_CATEGORY = "Creazione di una categoria radice";
	private final JPanel parentPanel;

	/**
	 * Constructor of the class
	 *
	 * @param parentPanel The {@link JPanel} used to display the dialog
	 */
	public AddRootCategoryAction(JPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	@Override
	public void run() {
		var newCategoryController = new NewCategoryController(new NewCategoryModel(), new NewCategoryView());

		var result = new OptionDialogDisplay()
				.setParentComponent(parentPanel)
				.setMessage(newCategoryController.getView().getMainJPanel())
				.setTitle(TITLE_CREATION_OF_A_ROOT_CATEGORY)
				.setMessageType(JOptionPane.QUESTION_MESSAGE)
				.show();

		if (result == JOptionPane.OK_OPTION) {
			try {
				CategoryManagerFactory.getManager()
						.addNewMainCategory(
								newCategoryController.getModel().getCategoryName(),
								newCategoryController.getModel().getCategoryDescription()
						);
			} catch (AlreadyExistException | InvalidArgumentException | NullObjectException e) {
				new MessageDialogDisplay()
						.setParentComponent(parentPanel)
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage(e.getMessage())
						.show();
			}
		}
	}
}
