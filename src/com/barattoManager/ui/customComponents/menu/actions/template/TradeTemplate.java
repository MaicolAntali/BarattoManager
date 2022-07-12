package com.barattoManager.ui.customComponents.menu.actions.template;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract class of a trade template
 */
public abstract class TradeTemplate extends NodeUuidActionTemplate {

	/**
	 * Error: No article has been selected
	 */
	private final String NO_ARTICLE_HAS_BEEN_SELECTED = "Non è stato selezionato nessun articolo";
	/**
	 * Error: You have to wait for the answer of the other user.
	 */
	private final String YOU_HAVE_TO_WAIT_FOR_OTHER_USER = "Devi aspettare la risposta dell'altro utente!";

	/**
	 * Implementation of customAction
	 * @param uuid {@link UUID} of trade
	 * @param tree {@link Tree}
	 * @param user {@link User}
	 */
	@Override
	protected void customAction(String uuid, Tree tree, User user) {
		var tradeOptional = TradeManager.getInstance().getTradeByUuid(uuid);

		if (tradeOptional.isEmpty()) {
			JOptionPane.showMessageDialog(tree, NO_ARTICLE_HAS_BEEN_SELECTED, "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (Objects.equals(tradeOptional.get().getAnswer().getWaitingUserAnswer(), user.getUsername())) {
			JOptionPane.showMessageDialog(tree, YOU_HAVE_TO_WAIT_FOR_OTHER_USER, "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		customAction(tradeOptional.get(), tree, user);
	}

	protected abstract void customAction(Trade trade, Tree tree, User user);

	/**
	 * Method used to change the state of the articles
	 * @param articleUuid {@link UUID} of the {@link Article}
	 * @param state new state of the {@link Article}
	 */
	public void changeArticleState(String articleUuid, Article.State state) {
		ArticleManager.getInstance().getArticleById(articleUuid)
				.orElseThrow(NullPointerException::new)
				.changeState(state);
	}
}
