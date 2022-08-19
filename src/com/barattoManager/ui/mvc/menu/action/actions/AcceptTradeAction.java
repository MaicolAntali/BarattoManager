package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.services.article.Article;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.menu.action.TradeBaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;

public class AcceptTradeAction extends TradeBaseAction {

	public AcceptTradeAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	@Override
	public void run() {
		var trade = getTrade();

		if (trade.isPresent()) {
			changeArticleState(trade.get().getArticleOneUuid(), Article.State.CLOSE_OFFER);
			changeArticleState(trade.get().getArticleTwoUuid(), Article.State.CLOSE_OFFER);
		}
	}
}
