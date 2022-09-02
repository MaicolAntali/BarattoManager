package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.menu.action.BaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;

public class CancelOfferAction extends BaseAction {


	public CancelOfferAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	@Override
	public void run() {

		var article = ArticleManagerFactory.getManager()
				.getArticleById(
						getUUIDFromNodes(
								getNodePath()
						)
				);

		if (article.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non è stato selezionato un articolo. Riprovare.")
					.show();
			return;
		}

		var option = JOptionPane.showConfirmDialog(
				getTreeController().getView().getMainJPanel(),
				"Sei sicuro di voler cambiare lo stato dell'articolo in: \"Offerta Cancellata\"?",
				"Cancella Offerta",
				JOptionPane.YES_NO_OPTION
		);

		if (option == JOptionPane.YES_NO_OPTION) {
			if (article.get().getArticleState() == Article.State.CANCELLED_OFFER) {
				new MessageDialogDisplay()
						.setParentComponent(getTreeController().getView().getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage("Lo stato di questo articolo è gia: \"Offerta Cancellata\"")
						.show();
				return;
			}

			if (article.get().getArticleState() != Article.State.OPEN_OFFER) {
				new MessageDialogDisplay()
						.setParentComponent(getTreeController().getView().getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage("Non è possibile cancellare un offerta nello stato: %s".formatted(article.get().getArticleState().toString()))
						.show();
				return;
			}

			try {
				ArticleManagerFactory.getManager()
						.changeArticleState(
								article.get().getUuid(),
								Article.State.CANCELLED_OFFER
						);
			} catch (InvalidArgumentException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
