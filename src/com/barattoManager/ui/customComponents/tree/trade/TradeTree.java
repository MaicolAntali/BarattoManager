package com.barattoManager.ui.customComponents.tree.trade;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.MeetManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.meet.Meet;
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
		Article articleOne = ArticleManager.getInstance().getArticleById(trade.articleOneUuid()).orElseThrow(NullPointerException::new);
		Article articleTwo = ArticleManager.getInstance().getArticleById(trade.articleTwoUuid()).orElseThrow(NullPointerException::new);
		Meet    meet       = MeetManager.getInstance().getMeetByUuid(trade.meetUuid()).orElseThrow(NullPointerException::new);

		var tradeNode = new DefaultMutableTreeNode("%s %s -> %s".formatted(
				articleOne.getArticleState() == Article.State.CLOSE_OFFER ? "✅" : "⏱",
				articleOne.getArticleName(),
				articleTwo.getArticleName()

		));


		tradeNode.add(new DefaultMutableTreeNode("Data di validità: %s".formatted(trade.tradeStartDateTime().format(DateTimeFormatter.ofPattern("hh:mm ~ dd/MM/yyyy")))));
		tradeNode.add(new DefaultMutableTreeNode("Data dell'incontro: %s".formatted(
				meet.getDateOfMeet().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
		)));
		tradeNode.add(new DefaultMutableTreeNode("Orario dell'incontro: %s ~ %s".formatted(
				meet.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm")),
				meet.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm"))

		)));
		tradeNode.add(new DefaultMutableTreeNode("UUID: %s".formatted(trade.uuid())));


		var articleOneNode = new DefaultMutableTreeNode(articleOne.getArticleName());
		articleOneNode.add(TreeUtils.generateFields(articleOne));
		articleOneNode.add(new DefaultMutableTreeNode("Stato: %s".formatted(articleOne.getArticleState().toString())));

		var articleTwoNode = new DefaultMutableTreeNode(articleTwo.getArticleName());
		articleTwoNode.add(TreeUtils.generateFields(articleTwo));
		articleTwoNode.add(new DefaultMutableTreeNode("Stato: %s".formatted(articleTwo.getArticleState().toString())));

		tradeNode.add(articleOneNode);
		tradeNode.add(articleTwoNode);

		return tradeNode;
	}

}
