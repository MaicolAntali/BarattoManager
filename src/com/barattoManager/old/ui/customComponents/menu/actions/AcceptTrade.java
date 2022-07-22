package com.barattoManager.old.ui.customComponents.menu.actions;

import com.barattoManager.old.sample.article.Article;
import com.barattoManager.old.sample.trade.Trade;
import com.barattoManager.old.sample.user.User;
import com.barattoManager.old.ui.customComponents.menu.actions.template.TradeTemplate;
import com.barattoManager.old.ui.customComponents.tree.Tree;

/**
 * Class used to create a {@link TradeTemplate} that permits to accept a trade
 */
public class AcceptTrade extends TradeTemplate {

	@Override
	protected void customAction(Trade trade, Tree tree, User user) {
		trade.closeTrade();

		changeArticleState(trade.getArticleOneUuid(), Article.State.CLOSE_OFFER);
		changeArticleState(trade.getArticleTwoUuid(), Article.State.CLOSE_OFFER);

	}
}
