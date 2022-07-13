package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.template.TradeTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

/**
 * Class that represent the action "Accept Trade"
 */
public class AcceptTrade extends TradeTemplate {

	/**
	 * Implementation of customAction
	 *
	 * @param trade {@link Trade} to accept
	 * @param tree  {@link Tree}
	 * @param user  {@link User}
	 */
	@Override
	protected void customAction(Trade trade, Tree tree, User user) {
		trade.closeTrade();

		changeArticleState(trade.getArticleOneUuid(), Article.State.CLOSE_OFFER);
		changeArticleState(trade.getArticleTwoUuid(), Article.State.CLOSE_OFFER);

	}
}
