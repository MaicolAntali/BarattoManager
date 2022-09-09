package com.barattoManager.ui.mvc.menu.action;

import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.services.Store;
import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.trade.Trade;
import com.barattoManager.services.trade.TradeManagerFactory;
import com.barattoManager.services.trade.TradeStatus;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;
import java.util.Optional;

/**
 * Abstract class that represent a trade base action
 */
public abstract class TradeBaseAction extends BaseAction {

	private static final String ERROR = "Errore";
	private static final String ERROR_IT_IS_NOT_POSSIBILE_DO_TO_OPERATIONS_ON_TRADE_IN_STATE_OF = "Non è possibile eseguire operazioni su un trade nello stato: %s";
	private static final String ERROR_WAIT_FOR_THE_OTHER_USER_ANWSER = "Non puoi eseguire nessuna operazione.\nDevi Aspettare la risposta dall'altro utente";
	private static final String ERROR_NO_TRADE_HAS_BEEN_SELECTED = "Non è stato selezionato un Trade";

	/**
	 * Constructor of the class
	 *
	 * @param user           {@link User} who has to do the action, who has logged in
	 * @param treeController {@link TreeController}
	 */
	public TradeBaseAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	/**
	 * Method used get an {@link Optional} of {@link Trade}
	 *
	 * @return {@link Optional} of {@link Trade}
	 */
	public Optional<Trade> getTrade() {
		var trade = TradeManagerFactory.getManager().getTradeByUuid(
				getUUIDFromNodes(getTreeController().getModel().getTreeNodes())
		);

		if (trade.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle(ERROR)
					.setMessage(ERROR_NO_TRADE_HAS_BEEN_SELECTED)
					.show();
			return Optional.empty();
		}

		if (trade.get().getAnswer().getWaitingUserAnswer().equalsIgnoreCase(Store.getLoggedUser().getUsername())) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle(ERROR)
					.setMessage(ERROR_WAIT_FOR_THE_OTHER_USER_ANWSER)
					.show();
			return Optional.empty();
		}

		if (trade.get().getTradeStatus() != TradeStatus.IN_PROGRESS) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle(ERROR)
					.setMessage(ERROR_IT_IS_NOT_POSSIBILE_DO_TO_OPERATIONS_ON_TRADE_IN_STATE_OF.formatted(trade.get().getTradeStatus().toString()))
					.show();
			return Optional.empty();
		}

		return trade;
	}

	/**
	 * Method used to change an article state
	 *
	 * @param articleUuid {@link Article} UUID
	 * @param state       new {@link Article} {@link Article.State}
	 */
	public void changeArticleState(String articleUuid, Article.State state) {
		try {
			ArticleManagerFactory.getManager()
					.changeArticleState(
							articleUuid,
							state
					);
		} catch (InvalidArgumentException e) {
			throw new RuntimeException(e);
		}
	}
}
