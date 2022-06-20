package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.exception.NullCategoryException;
import com.barattoManager.manager.ArticleManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.tree.TreeNode;

/**
 * Class used to cancel an offer
 */
public class CancelOffer implements MenuAction {

	@Override
	public void run(RepaintEventHandler repaintEventHandler, User user, Tree tree) {
		try {
			// Get the selected node
			TreeNode[] nodePath = tree.getSelectedPathNode();

			var result = JOptionPane.showConfirmDialog(
					tree,
					"Sei sicuro di voler cambiare lo stato dell'articolo in: \"Offerta Cancellata\"?",
					"Conferma Operazione",
					JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				var article = ArticleManager.getInstance().getArticleById(nodePath[nodePath.length-1].toString());

				if (article.isPresent()) {
					if (article.get().getArticleState() != Article.State.CANCELLED_OFFER) {
						article.get().changeState(Article.State.CANCELLED_OFFER);
						repaintEventHandler.fireListeners();
					}
					else {
						throw new IllegalValuesException("Lo stato di questo articolo è gia: \"Offerta Cancellata\"");
					}
				}
				else {
					throw new  NullCategoryException("Non è stato selezionato un Articolo.\nRiprovare selezionando il nodo madre dell'articolo.");
				}
			}
		} catch (NoNodeSelected | NullCategoryException | IllegalValuesException ex) {
			JOptionPane.showMessageDialog(tree, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}
