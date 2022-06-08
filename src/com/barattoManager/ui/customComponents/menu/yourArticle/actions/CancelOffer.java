package com.barattoManager.ui.customComponents.menu.yourArticle.actions;

import com.barattoManager.article.Article;
import com.barattoManager.article.ArticleManager;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.exception.NullCategoryException;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;
import com.barattoManager.user.User;

import javax.swing.*;
import javax.swing.tree.TreeNode;

/**
 * Class used to cancel an offer
 */
public class CancelOffer implements MenuItemAction {
	@Override
	public void run(JPanel fatherPanel, RepaintEventHandler repaintEventHandler, User user, ArticleTree articleTree) {
		try {
			// Get the selected node
			TreeNode[] nodePath = articleTree.getSelectedPathNode();

			var result = JOptionPane.showConfirmDialog(
					fatherPanel,
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
			JOptionPane.showMessageDialog(fatherPanel, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}
