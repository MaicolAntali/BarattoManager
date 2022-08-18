package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.menu.action.BaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;

public class NewTradeAction extends BaseAction {


	public NewTradeAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	@Override
	public void run() {
		var article = ArticleManagerFactory.getManager().getArticleById(
				getUUIDFromNodes(getNodePath(getUser(), getTreeController()))
		);

		if (article.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non è stato selezionato uno scambio")
					.show();
			return;
		}

		var articlesUserCanTrade = ArticleManagerFactory.getManager()
				.getArticlesByOwnerStateCategory(getUser().getUsername(), Article.State.OPEN_OFFER, article.get().getCategoryUuid());

		if (articlesUserCanTrade.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non possiedi nessun articolo che può essere barattato con l'articolo selezionato.")
					.show();
			return;
		}

	}
}
