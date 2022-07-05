package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.template.NodeUuidActionTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;

/**
 * Class used to cancel an offer
 */
public class CancelOffer extends NodeUuidActionTemplate {

	@Override
	protected void customAction(String uuid, Tree tree, User user) {
		var article = ArticleManager.getInstance().getArticleById(uuid);

		if (article.isEmpty()) {
			JOptionPane.showMessageDialog(tree, "Non è stato selezionato un articolo", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		var result = JOptionPane.showConfirmDialog(
				tree,
				"Sei sicuro di voler cambiare lo stato dell'articolo in: \"Offerta Cancellata\"?",
				"Conferma Operazione",
				JOptionPane.YES_NO_OPTION);


		if (result == JOptionPane.YES_OPTION) {
			if (article.get().getArticleState() == Article.State.CANCELLED_OFFER) {
				JOptionPane.showMessageDialog(tree, "Lo stato di questo articolo è gia: \"Offerta Cancellata\"", "Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}

			article.get().changeState(Article.State.CANCELLED_OFFER);
		}
	}
}
