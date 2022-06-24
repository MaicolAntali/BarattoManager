package com.barattoManager.ui.customComponents.tree.trade;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.MeetManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;
import com.barattoManager.utils.TreeUtils;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TradeTree extends Tree {

	private DefaultMutableTreeNode rootNode;

	public TradeTree(Dimension dimension, User user) {
		super(dimension);

		for (Trade trade : TradeManager.getInstance().getTradeByUser(user.getUsername())) {
			var node = createTradeNode(trade);
			getRootNode().add(node);
		}

		getTree().expandPath(new TreePath(getRootNode()));
	}

	public TradeTree(User user) {
		this(new Dimension(500, 290), user);
	}

	@Override
	protected DefaultMutableTreeNode getRootNode() {
		if (rootNode == null)
			rootNode = new DefaultMutableTreeNode("Scambi");

		return rootNode;
	}

	private DefaultMutableTreeNode createTradeNode(Trade trade) {
		var articleNode = new DefaultMutableTreeNode("%s -> %s".formatted(
				ArticleManager.getInstance().getArticleById(trade.articleOneUuid()).orElseThrow(NullPointerException::new).getArticleName(),
				ArticleManager.getInstance().getArticleById(trade.articleTwoUuid()).orElseThrow(NullPointerException::new).getArticleName()

		));


		articleNode.add(new DefaultMutableTreeNode("Data di validità: %s".formatted(trade.tradeStartDateTime().format(DateTimeFormatter.ofPattern("hh:mm ~ dd/MM/yyyy")))));
		articleNode.add(new DefaultMutableTreeNode("Data dell'incontro: %s".formatted(
				MeetManager.getInstance().getMeetByUuid(trade.meetUuid()).orElseThrow(NullPointerException::new).getDateOfMeet().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
		)));
		articleNode.add(new DefaultMutableTreeNode("Orario dell'incontro: %s ~ %s".formatted(
				MeetManager.getInstance().getMeetByUuid(trade.meetUuid()).orElseThrow(NullPointerException::new).getStartTime().format(DateTimeFormatter.ofPattern("hh:mm")),
				MeetManager.getInstance().getMeetByUuid(trade.meetUuid()).orElseThrow(NullPointerException::new).getEndTime().format(DateTimeFormatter.ofPattern("hh:mm"))

		)));


		var articleOneNode = new DefaultMutableTreeNode(ArticleManager.getInstance().getArticleById(trade.articleOneUuid()).orElseThrow(NullPointerException::new).getArticleName());
		articleOneNode.add(TreeUtils.generateFields(ArticleManager.getInstance().getArticleById(trade.articleOneUuid()).orElseThrow(NullPointerException::new)));

		var articleTwoNode = new DefaultMutableTreeNode(ArticleManager.getInstance().getArticleById(trade.articleTwoUuid()).orElseThrow(NullPointerException::new).getArticleName());
		articleTwoNode.add(TreeUtils.generateFields(ArticleManager.getInstance().getArticleById(trade.articleTwoUuid()).orElseThrow(NullPointerException::new)));

		articleNode.add(articleOneNode);
		articleNode.add(articleTwoNode);

		return articleNode;
	}

}
