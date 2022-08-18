package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.dialogs.NewArticle.NewArticleController;
import com.barattoManager.ui.mvc.dialogs.NewArticle.NewArticleModel;
import com.barattoManager.ui.mvc.dialogs.NewArticle.NewArticleView;
import com.barattoManager.ui.mvc.dialogs.selectCategory.SelectCategoryController;
import com.barattoManager.ui.mvc.dialogs.selectCategory.SelectCategoryModel;
import com.barattoManager.ui.mvc.dialogs.selectCategory.SelectCategoryView;
import com.barattoManager.ui.mvc.menu.action.BaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;

public class NewArticleAction extends BaseAction {

	public NewArticleAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	@Override
	public void run() {

		var selectCategoryController = new SelectCategoryController(
				new SelectCategoryModel(CategoryManagerFactory.getManager().getRootCategoryMap().values().stream().toList()),
				new SelectCategoryView()
		);

		var option = new OptionDialogDisplay()
				.setParentComponent(getTreeController().getView().getMainJPanel())
				.setMessage(selectCategoryController.getView().getMainJPanel())
				.setTitle("Selezione Categoria")
				.show();

		if (option == JOptionPane.OK_OPTION) {
			var categorySelected = SelectCategoryController.getCategoryFromCategoryPath(
					selectCategoryController.getModel().getCategoryNamesSelected()
			);

			if (categorySelected.isEmpty()) {
				new MessageDialogDisplay()
						.setParentComponent(getTreeController().getView().getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage("Non è stata seleziona nessuna categoria")
						.show();
				return;
			}

			var newArticleController = new NewArticleController(
					new NewArticleModel(
							categorySelected.get()
					),
					new NewArticleView()
			);

			var articleOption = new OptionDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessage(newArticleController.getView().getMainJPanel())
					.setTitle("Selezione Categoria")
					.show();

			if (articleOption == JOptionPane.OK_OPTION) {

				if (newArticleController.getModel().getArticleName().isBlank()) {
					new MessageDialogDisplay()
							.setParentComponent(getTreeController().getView().getMainJPanel())
							.setMessageType(JOptionPane.ERROR_MESSAGE)
							.setTitle("Errore")
							.setMessage("Il nome dell'articolo è vuoto.")
							.show();
					return;
				}

				ArticleManagerFactory.getManager()
						.addNewArticle(
								newArticleController.getModel().getArticleName(),
								getUser().getUsername(),
								categorySelected.get().getUuid(),
								newArticleController.getModel().getArticleFields(),
								newArticleController.getModel().getArticleFieldValues()
						);

			}
		}
	}
}
