package com.barattoManager.ui.mvc.configurator.categoryEditor;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.exception.NullObjectException;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.dialogs.newCategory.NewCategoryController;
import com.barattoManager.ui.mvc.dialogs.newCategory.NewCategoryModel;
import com.barattoManager.ui.mvc.dialogs.newCategory.NewCategoryView;
import com.barattoManager.ui.mvc.dialogs.newField.NewFieldController;
import com.barattoManager.ui.mvc.dialogs.newField.NewFieldModel;
import com.barattoManager.ui.mvc.dialogs.newField.NewFieldView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.tree.TreeUtils;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeController;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeModel;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeView;
import com.barattoManager.ui.utils.ControllerNames;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;
import javax.swing.tree.TreeNode;

public class CategoryEditorController implements BaseController {

	private final CategoryEditorView view;
	private final CategoryTreeController categoryTreeController;

	public CategoryEditorController(CategoryEditorView view) {
		this.view = view;

		ActionListenerInstaller.processAnnotations(this, view);

		categoryTreeController = new CategoryTreeController(
				new CategoryTreeModel(
						CategoryManagerFactory.getManager().getRootCategoryMap().values().stream().toList()
				),
				new CategoryTreeView()
		);

		this.view.setTreePanel(categoryTreeController.getView().getMainJPanel());
	}

	@Override
	public BaseModel getModel() {
		return null;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "backToHome")
	private void clickOnBackToHome() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_CONFIGURATOR.toString());
	}

	@ActionListenerFor(sourceField = "addRootCategoryButton")
	private void clickAddRootCategoryButton() {

		var newCategoryController = new NewCategoryController(new NewCategoryModel(), new NewCategoryView());

		var result = new OptionDialogDisplay()
				.setParentComponent(view.getMainJPanel())
				.setMessage(newCategoryController.getView().getMainJPanel())
				.setTitle("Creazione di una categoria radice")
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
						.setParentComponent(view.getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage(e.getMessage())
						.show();
			}
		}
	}

	@ActionListenerFor(sourceField = "addSubCategoryButton")
	private void clickOnAddSubCategoryButton() {

		TreeNode[] treeNodes = categoryTreeController.getModel().getTreeNodes();
		if (treeNodes == null || treeNodes.length == 1) {
			new MessageDialogDisplay()
					.setParentComponent(view.getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Per favore Selezionare un nodo valido.")
					.show();
			return;
		}

		var newCategoryController = new NewCategoryController(new NewCategoryModel(), new NewCategoryView());

		var result = new OptionDialogDisplay()
				.setParentComponent(view.getMainJPanel())
				.setMessage(newCategoryController.getView().getMainJPanel())
				.setTitle("Creazione di una categoria radice")
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
						.setParentComponent(view.getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage(e.getMessage())
						.show();
			}
		}
	}

	@ActionListenerFor(sourceField = "addFieldButton")
	private void clickOnAddFieldButton() {

		TreeNode[] treeNodes = categoryTreeController.getModel().getTreeNodes();
		if (treeNodes == null) {
			new MessageDialogDisplay()
					.setParentComponent(view.getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Per favore Selezionare un nodo.")
					.show();
			return;
		}

		var newFieldController = new NewFieldController(new NewFieldModel(), new NewFieldView());

		var result = new OptionDialogDisplay()
				.setParentComponent(view.getMainJPanel())
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
						.setParentComponent(view.getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage(e.getMessage())
						.show();
			}
		}
	}
}
