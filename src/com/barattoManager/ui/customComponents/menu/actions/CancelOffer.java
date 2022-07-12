package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.factory.ArticleManagerFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.template.NodeUuidActionTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;

/**
 * Class used to cancel an offer
 */
public class CancelOffer extends NodeUuidActionTemplate {
	/**
	 * Error: no article has been selected
	 */
	private static final String ERROR_NO_ARTICLE_SELECTED = "Non è stato selezionato un articolo";
	/**
	 * Message: confirmation asked to change the article status to 'Offer deleted'
	 */
	private static final String CONFIRM_OPERATION_CANCEL_OFFER = "Sei sicuro di voler cambiare lo stato dell'articolo in: \"Offerta Cancellata\"?";
	/**
	 * Error: the state of the article is already 'offer deleted'
	 */
	private static final String ERROR_STATUS_ARTICLE_IS_ALREADY_CANCELED = "Lo stato di questo articolo è gia: \"Offerta Cancellata\"";


	@Override
	protected void customAction(String uuid, Tree tree, User user) {
		var article = ArticleManagerFactory.getManager().getArticleById(uuid);

		if (article.isEmpty()) {
			JOptionPane.showMessageDialog(tree, ERROR_NO_ARTICLE_SELECTED, "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		var result = JOptionPane.showConfirmDialog(
				tree,
				CONFIRM_OPERATION_CANCEL_OFFER,
				"Conferma Operazione",
				JOptionPane.YES_NO_OPTION);


		if (result == JOptionPane.YES_OPTION) {
			if (article.get().getArticleState() == Article.State.CANCELLED_OFFER) {
				JOptionPane.showMessageDialog(tree, ERROR_STATUS_ARTICLE_IS_ALREADY_CANCELED, "Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				ArticleManagerFactory.getManager()
						.changeArticleState(
								article.get().getUuid(),
								Article.State.CANCELLED_OFFER
						);
			} catch (IllegalValuesException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
