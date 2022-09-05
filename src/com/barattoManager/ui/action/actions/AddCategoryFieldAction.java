package com.barattoManager.ui.action.actions;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.exception.NullObjectException;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.action.Action;
import com.barattoManager.ui.mvc.dialogs.newField.NewFieldController;
import com.barattoManager.ui.mvc.dialogs.newField.NewFieldModel;
import com.barattoManager.ui.mvc.dialogs.newField.NewFieldView;
import com.barattoManager.ui.mvc.tree.TreeUtils;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;
import javax.swing.tree.TreeNode;

public class AddCategoryFieldAction implements Action {

	private final CategoryTreeController categoryTreeController;
	private final JPanel parentPanel;

	public AddCategoryFieldAction(CategoryTreeController categoryTreeController, JPanel parentPanel) {
		this.categoryTreeController = categoryTreeController;
		this.parentPanel = parentPanel;
	}

	@Override
	public void run() {
		TreeNode[] treeNodes = categoryTreeController.getModel().getTreeNodes();
		if (treeNodes == null) {
			new MessageDialogDisplay()
					.setParentComponent(parentPanel)
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Per favore Selezionare un nodo.")
					.show();
			return;
		}

		var newFieldController = new NewFieldController(new NewFieldModel(), new NewFieldView());

		var result = new OptionDialogDisplay()
				.setParentComponent(parentPanel)
				.setMessage(newFieldController.getView().getMainJPanel())
				.setTitle("Creazione di una categoria radice")
				.setMessageType(JOptionPane.QUESTION_MESSAGE)
				.show();

		if (result == JOptionPane.OK_OPTION) {
			try {
				CategoryManagerFactory.getManager()
						.addNewField(
								TreeUtils.treeNodeArrayToArrayList(treeNodes, "~"),
								newFieldController.getModel().getFieldName(),
								newFieldController.getModel().isFieldRequired()
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
