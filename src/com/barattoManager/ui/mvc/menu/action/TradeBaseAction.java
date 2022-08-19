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

public abstract class TradeBaseAction extends BaseAction {

	public TradeBaseAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	public Optional<Trade> getTrade() {
		var trade = TradeManagerFactory.getManager().getTradeByUuid(
				getUUIDFromNodes(getTreeController().getModel().getTreeNodes())
		);

		if (trade.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non è stato selezionato un Trade")
					.show();
			return Optional.empty();
		}

		if (trade.get().getAnswer().getWaitingUserAnswer().equalsIgnoreCase(Store.getLoggedUser().getUsername())) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non puoi eseguire nessuna operazione.\nDevi Aspettare la risposta dall'altro utente")
					.show();
			return Optional.empty();
		}

		if (trade.get().getTradeStatus() != TradeStatus.IN_PROGRESS) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non è possibile eseguire operazioni su un trade nello stato: %s".formatted(trade.get().getTradeStatus().toString()))
					.show();
			return Optional.empty();
		}

		return trade;
	}

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
