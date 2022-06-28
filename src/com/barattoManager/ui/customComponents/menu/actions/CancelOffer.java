package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.template.NodeActionTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.tree.TreeNode;

/**
 * Class used to cancel an offer
 */
public class CancelOffer extends NodeActionTemplate {
	@Override
	protected void customAction(TreeNode[] nodePath, Tree tree, User user) {
		var result = JOptionPane.showConfirmDialog(
				tree,
				"Sei sicuro di voler cambiare lo stato dell'articolo in: \"Offerta Cancellata\"?",
				"Conferma Operazione",
				JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			var article = ArticleManager.getInstance().getArticleById(nodePath[nodePath.length - 1].toString());

			if (article.isEmpty()) {
				JOptionPane.showMessageDialog(tree, "Non è stato selezionato un Articolo.\nRiprovare selezionando il nodo madre dell'articolo.", "Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (article.get().getArticleState() == Article.State.CANCELLED_OFFER) {
				JOptionPane.showMessageDialog(tree, "Lo stato di questo articolo è gia: \"Offerta Cancellata\"", "Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}

			article.get().changeState(Article.State.CANCELLED_OFFER);
		}
	}
}
