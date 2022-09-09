package com.barattoManager.ui.action.actions;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.exception.NullObjectException;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.action.Action;
import com.barattoManager.ui.mvc.dialogs.newCategory.NewCategoryController;
import com.barattoManager.ui.mvc.dialogs.newCategory.NewCategoryModel;
import com.barattoManager.ui.mvc.dialogs.newCategory.NewCategoryView;
import com.barattoManager.ui.mvc.tree.TreeUtils;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;
import javax.swing.tree.TreeNode;

/**
 * Action used to add a sub category
 */
public class AddSubCategoryAction implements Action {

	private static final String TITLE_CREATION_OF_A_ROOT_CATEGORY = "Creazione di una sotto-categoria radice";
	private static final String MESSAGE_CHOOSE_A_VALID_NODE = "Per favore selezionare un nodo valido.";
	private static final String ERROR = "Errore";
	private final CategoryTreeController categoryTreeController;
	private final JPanel parentPanel;

	/**
	 * Constructor of the class
	 *
	 * @param categoryTreeController used to get the selected node
	 * @param parentPanel            The {@link JPanel} used to display the dialog
	 */
	public AddSubCategoryAction(CategoryTreeController categoryTreeController, JPanel parentPanel) {
		this.categoryTreeController = categoryTreeController;
		this.parentPanel = parentPanel;
	}

	@Override
	public void run() {
		TreeNode[] treeNodes = categoryTreeController.getModel().getTreeNodes();
		if (treeNodes == null || treeNodes.length == 1) {
			new MessageDialogDisplay()
					.setParentComponent(parentPanel)
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle(ERROR)
					.setMessage(MESSAGE_CHOOSE_A_VALID_NODE)
					.show();
			return;
		}

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
						.addNewSubCategory(
								TreeUtils.treeNodeArrayToArrayList(treeNodes, "~"),
								newCategoryController.getModel().getCategoryName(),
								newCategoryController.getModel().getCategoryDescription()
						);
			} catch (AlreadyExistException | InvalidArgumentException | NullObjectException e) {
				new MessageDialogDisplay()
						.setParentComponent(parentPanel)
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle(ERROR)
						.setMessage(e.getMessage())
						.show();
			}
		}
	}
}
