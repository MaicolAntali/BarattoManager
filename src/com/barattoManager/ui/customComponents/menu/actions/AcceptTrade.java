package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.template.TradeTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

public class AcceptTrade extends TradeTemplate {

	@Override
	protected void customAction(Trade trade, Tree tree, User user) {
		trade.closeTrade();

		changeArticleState(trade.getArticleOneUuid(), Article.State.CLOSE_OFFER);
		changeArticleState(trade.getArticleTwoUuid(), Article.State.CLOSE_OFFER);

	}
}
