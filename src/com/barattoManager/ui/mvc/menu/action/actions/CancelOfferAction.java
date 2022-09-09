package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.menu.action.BaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;

/**
 * BaseAction used to cancel an offer
 */
public class CancelOfferAction extends BaseAction {


	private static final String MESSAGE_NO_ARTICLE_HAS_BEEN_CHOSEN = "Non è stato selezionato un articolo. Riprovare.";
	private static final String MESSAGE_SURE_TO_CANCEL_THE_OFFER = "Sei sicuro di voler cambiare lo stato dell'articolo in: \"Offerta Cancellata\"?";
	private static final String TITLE_CANCEL_OFFER = "Cancella Offerta";
	private static final String ERROR_STATE_IS_ALREADY_CANCELED = "Lo stato di questo articolo è gia: \"Offerta Cancellata\"";
	private static final String ERROR_IS_NOT_POSSIBILE_TO_CHANGE_FROM_CANCEL_OFFER = "Non è possibile cancellare un offerta nello stato: %s";
	private static final String ERROR = "Errore";

	/**
	 * Constructor of the class
	 *
	 * @param user           {@link User} who has to do the action, who has logged in
	 * @param treeController {@link TreeController}
	 */
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
					.setTitle(ERROR)
					.setMessage(MESSAGE_NO_ARTICLE_HAS_BEEN_CHOSEN)
					.show();
			return;
		}

		var option = JOptionPane.showConfirmDialog(
				getTreeController().getView().getMainJPanel(),
				MESSAGE_SURE_TO_CANCEL_THE_OFFER,
				TITLE_CANCEL_OFFER,
				JOptionPane.YES_NO_OPTION
		);

		if (option == JOptionPane.YES_NO_OPTION) {
			if (article.get().getArticleState() == Article.State.CANCELLED_OFFER) {
				new MessageDialogDisplay()
						.setParentComponent(getTreeController().getView().getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle(ERROR)
						.setMessage(ERROR_STATE_IS_ALREADY_CANCELED)
						.show();
				return;
			}

			if (article.get().getArticleState() != Article.State.OPEN_OFFER) {
				new MessageDialogDisplay()
						.setParentComponent(getTreeController().getView().getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle(ERROR)
						.setMessage(ERROR_IS_NOT_POSSIBILE_TO_CHANGE_FROM_CANCEL_OFFER.formatted(article.get().getArticleState().toString()))
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
