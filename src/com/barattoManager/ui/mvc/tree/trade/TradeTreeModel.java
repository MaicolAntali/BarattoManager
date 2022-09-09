package com.barattoManager.ui.mvc.tree.trade;

import com.barattoManager.services.Store;
import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.trade.Trade;
import com.barattoManager.ui.mvc.tree.TreeModel;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeController;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Model of {@link TradeTreeController} that contains the data
 */
public class TradeTreeModel extends TreeModel<Trade> {

	/**
	 * Constructor of the class
	 * @param data {@link List}
	 */
	public TradeTreeModel(List<Trade> data) {
		super(filterList(data));
	}

	@Override
	public void update(ConcurrentHashMap<String, Trade> updatedMap) {
		setData(filterList(updatedMap.values().stream().toList()));
		fireModelDataHasChangeListener();
	}

	private static List<Trade> filterList(List<Trade> trades) {
		return trades.stream()
				.filter(trade ->
						Objects.equals(
								ArticleManagerFactory.getManager().getArticleById(
										trade.getArticleOneUuid()
								).orElseThrow(NullPointerException::new).getUserNameOwner(), Store.getLoggedUser().getUsername())
								|| Objects.equals(
								ArticleManagerFactory.getManager().getArticleById(
										trade.getArticleTwoUuid()
								).orElseThrow(NullPointerException::new).getUserNameOwner(), Store.getLoggedUser().getUsername()))
				.toList();
	}
}
