package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.template.TradeTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

public class AcceptTrade extends TradeTemplate {

	@Override
	protected void customAction(Trade trade, Tree tree, User user) {
		trade.closeTrade();
		closeOfferArticle(trade.getArticleOneUuid());
		closeOfferArticle(trade.getArticleTwoUuid());
	}

	private void closeOfferArticle(String articleUuid) {
		ArticleManager.getInstance().getArticleById(articleUuid)
				.orElseThrow(NullPointerException::new)
				.changeState(Article.State.CLOSE_OFFER);
	}
}
