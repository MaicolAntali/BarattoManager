package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.dialogs.NewArticle.NewArticleController;
import com.barattoManager.ui.mvc.dialogs.NewArticle.NewArticleModel;
import com.barattoManager.ui.mvc.dialogs.NewArticle.NewArticleView;
import com.barattoManager.ui.mvc.dialogs.select.selectCategory.SelectCategoryController;
import com.barattoManager.ui.mvc.dialogs.select.selectCategory.SelectCategoryModel;
import com.barattoManager.ui.mvc.dialogs.select.selectCategory.SelectCategoryView;
import com.barattoManager.ui.mvc.menu.action.BaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;

/**
 * BaseAction used to add a new article
 */
public class NewArticleAction extends BaseAction {

	private static final String TITLE_SELECT_CATEGORY = "Selezione Categoria";
	private static final String ERROR = "Errore";
	private static final String MESSAGE_NO_CATEGORY_HAS_BEEN_SELECTED = "Non è stata seleziona nessuna categoria";
	private static final String MESSAGE_NAME_OF_ARTICLE_IS_EMPTY = "Il nome dell'articolo è vuoto.";

	/**
	 * Constructor of the class
	 *
	 * @param user           {@link User} who has to do the action, who has logged in
	 * @param treeController {@link TreeController}
	 */
	public NewArticleAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	@Override
	public void run() {

		var selectCategoryController = new SelectCategoryController(
				new SelectCategoryModel(CategoryManagerFactory.getManager().getRootCategoryMap().values().stream().toList()),
				new SelectCategoryView(String.class)
		);

		var option = new OptionDialogDisplay()
				.setParentComponent(getTreeController().getView().getMainJPanel())
				.setMessage(selectCategoryController.getView().getMainJPanel())
				.setTitle(TITLE_SELECT_CATEGORY)
				.show();

		if (option == JOptionPane.OK_OPTION) {
			var categorySelected = SelectCategoryController.getCategoryFromCategoryPath(
					selectCategoryController.getModel().getCategoryNamesSelected()
			);

			if (categorySelected.isEmpty()) {
				new MessageDialogDisplay()
						.setParentComponent(getTreeController().getView().getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle(ERROR)
						.setMessage(MESSAGE_NO_CATEGORY_HAS_BEEN_SELECTED)
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
					.setTitle(TITLE_SELECT_CATEGORY)
					.show();

			if (articleOption == JOptionPane.OK_OPTION) {

				if (newArticleController.getModel().getArticleName().isBlank()) {
					new MessageDialogDisplay()
							.setParentComponent(getTreeController().getView().getMainJPanel())
							.setMessageType(JOptionPane.ERROR_MESSAGE)
							.setTitle(ERROR)
							.setMessage(MESSAGE_NAME_OF_ARTICLE_IS_EMPTY)
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
