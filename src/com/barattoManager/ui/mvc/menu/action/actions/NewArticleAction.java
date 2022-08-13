package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.dialogs.selectCategory.SelectCategoryController;
import com.barattoManager.ui.mvc.dialogs.selectCategory.SelectCategoryModel;
import com.barattoManager.ui.mvc.dialogs.selectCategory.SelectCategoryView;
import com.barattoManager.ui.mvc.menu.action.BaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;

public class NewArticleAction extends BaseAction {

	private final User user;
	private final TreeController<?> treeController;

	public NewArticleAction(User user, TreeController<?> treeController) {
		this.user = user;
		this.treeController = treeController;
	}

	@Override
	public void run() {

		var selectCategoryController = new SelectCategoryController(
				new SelectCategoryModel(CategoryManagerFactory.getManager().getRootCategoryMap().values().stream().toList()),
				new SelectCategoryView()
		);

		var option = new OptionDialogDisplay()
				.setParentComponent(treeController.getView().getMainJPanel())
				.setMessage(selectCategoryController.getView().getMainJPanel())
				.setTitle("Selezione Categoria")
				.show();

		if (option == JOptionPane.OK_OPTION)
			System.out.println(selectCategoryController.getView().getSelectedCategoryName());
	}
}
