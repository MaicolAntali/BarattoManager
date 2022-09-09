package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.services.article.Article;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.menu.action.TradeBaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;

/**
 * TradeBaseAction used to accept a trade
 */
public class AcceptTradeAction extends TradeBaseAction {

	/**
	 * Constructor of the class
	 * @param user {@link User} who has to do the action, who has logged in
	 * @param treeController {@link TreeController}
	 */
	public AcceptTradeAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	@Override
	public void run() {
		var trade = getTrade();

		if (trade.isPresent()) {
			trade.get().closeTrade();

			changeArticleState(trade.get().getArticleOneUuid(), Article.State.CLOSE_OFFER);
			changeArticleState(trade.get().getArticleTwoUuid(), Article.State.CLOSE_OFFER);
		}
	}
}
