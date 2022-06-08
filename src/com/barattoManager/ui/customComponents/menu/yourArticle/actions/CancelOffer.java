package com.barattoManager.ui.customComponents.menu.yourArticle.actions;

import com.barattoManager.article.Article;
import com.barattoManager.article.ArticleManager;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.exception.NullCategoryException;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.event.RepaintListener;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;
import com.barattoManager.user.User;

import javax.swing.*;
import javax.swing.tree.TreeNode;

/**
 * Class used to cancel an offer
 */
public class CancelOffer implements MenuItemAction {

	/**
	 * Message: Are you sure to change the state of article to cancelled offer?
	 */
	private static final String ARE_YOU_SURE_TO_CHANGE_THE_STATE_TO_CANCELLED = "Sei sicuro di voler cambiare lo stato dell'articolo in: \"Offerta Cancellata\"?";
	/**
	 * Title: Confirm Operation
	 */
	private static final String TITLE_CONFIRM = "Conferma Operazione";
	/**
	 * Message: The state of this article is already: Cancelled Offer
	 */
	private static final String THE_STATE_IS_ALREADY_CANCELLED = "Lo stato di questo articolo è gia: \"Offerta Cancellata\"";
	/**
	 * Error: No node has been selected, Try again by selecting the parent node of the article.
	 */
	private static final String NO_NODE_HAS_BEEN_SELECTED_SELECT_A_PARENT_NODE_ERROR = "Non è stato selezionato un Articolo.\nRiprovare selezionando il nodo madre dell'articolo.";

	/**
	 * Implementation of the method {@link MenuItemAction#run(JPanel, RepaintEventHandler, User, ArticleTree)}
	 * Method used to cancel an offer
	 * @param fatherPanel {@link JPanel}
	 * @param repaintEventHandler {@link RepaintEventHandler} object
	 * @param user {@link User} object
	 * @param articleTree {@link ArticleTree} object
	 */
	@Override
	public void run(JPanel fatherPanel, RepaintEventHandler repaintEventHandler, User user, ArticleTree articleTree) {
		try {
			// Get the selected node
			TreeNode[] nodePath = articleTree.getSelectedPathNode();

			var result = JOptionPane.showConfirmDialog(
					fatherPanel,
					ARE_YOU_SURE_TO_CHANGE_THE_STATE_TO_CANCELLED,
					TITLE_CONFIRM,
					JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				var article = ArticleManager.getInstance().getArticleById(nodePath[nodePath.length-1].toString());

				if (article.isPresent()) {
					if (article.get().getArticleState() != Article.State.CANCELLED_OFFER) {
						article.get().changeState(Article.State.CANCELLED_OFFER);
						repaintEventHandler.fireListeners();
					}
					else {
						throw new IllegalValuesException(THE_STATE_IS_ALREADY_CANCELLED);
					}
				}
				else {
					throw new  NullCategoryException(NO_NODE_HAS_BEEN_SELECTED_SELECT_A_PARENT_NODE_ERROR);
				}
			}
		} catch (NoNodeSelected | NullCategoryException | IllegalValuesException ex) {
			JOptionPane.showMessageDialog(fatherPanel, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}
