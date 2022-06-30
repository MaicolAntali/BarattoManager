package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.template.NodeUuidActionTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;

public class AcceptTrade extends NodeUuidActionTemplate {

	@Override
	protected void customAction(String uuid, Tree tree, User user) {
		var tradeOptional = TradeManager.getInstance().getTradeByUuid(uuid);

		if (tradeOptional.isEmpty()) {
			JOptionPane.showMessageDialog(tree, "Non è stato selezionato nessun articolo", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		tradeOptional.get().closeTrade();
		closeOfferArticle(tradeOptional.get().getArticleOneUuid());
		closeOfferArticle(tradeOptional.get().getArticleTwoUuid());
	}

	private void closeOfferArticle(String articleUuid) {
		ArticleManager.getInstance().getArticleById(articleUuid)
				.orElseThrow(NullPointerException::new)
				.changeState(Article.State.CLOSE_OFFER);
	}
}
