package com.barattoManager.ui.customComponents.tree.trade;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.MeetManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.meet.Meet;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.trade.TradeStatus;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;
import com.barattoManager.utils.TreeUtils;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TradeTree extends Tree {

	private DefaultMutableTreeNode rootNode;

	public TradeTree(Dimension dimension, User user) {
		super(dimension);

		var nodeMap = new HashMap<String, ArrayList<DefaultMutableTreeNode>>();
		TradeManager.getInstance().getTradeByUser(user.getUsername())
				.forEach(trade -> {

					var tradeKey = trade.getTradeStatus() != TradeStatus.IN_PROGRESS
							? trade.getTradeStatus().toString()
							: Objects.equals(trade.getAnswer().getWaitingUserAnswer(), user.getUsername())
							? "Stai attendendo risposta" : "Devi rispondere";


					if (!nodeMap.containsKey(tradeKey)) {
						nodeMap.put(tradeKey, new ArrayList<>());
					}

					nodeMap.get(tradeKey).add(createTradeNode(trade));
				});

		nodeMap.forEach((key, value) -> {
			var stateNode = new DefaultMutableTreeNode(key);
			getRootNode().add(stateNode);
			value.forEach(stateNode::add);
		});

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
		Article articleOne = ArticleManager.getInstance().getArticleById(trade.getArticleOneUuid()).orElseThrow(NullPointerException::new);
		Article articleTwo = ArticleManager.getInstance().getArticleById(trade.getArticleTwoUuid()).orElseThrow(NullPointerException::new);
		Meet    meet       = MeetManager.getInstance().getMeetByUuid(trade.getMeetUuid()).orElseThrow(NullPointerException::new);

		var tradeNode = new DefaultMutableTreeNode("%s %s -> %s".formatted(
				trade.getTradeStatus() == TradeStatus.IN_PROGRESS ? "⏱" :
						trade.getTradeStatus() == TradeStatus.CLOSED ? "✅" : "❌",
				articleOne.getArticleName(),
				articleTwo.getArticleName()

		));


		tradeNode.add(new DefaultMutableTreeNode("Data di validità: %s".formatted(trade.getTradeStartDateTime().format(DateTimeFormatter.ofPattern("hh:mm ~ dd/MM/yyyy")))));
		tradeNode.add(new DefaultMutableTreeNode("Data dell'incontro: %s".formatted(
				meet.getDateOfMeet().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
		)));
		tradeNode.add(new DefaultMutableTreeNode("Orario dell'incontro: %s ~ %s".formatted(
				meet.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm")),
				meet.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm"))

		)));
		tradeNode.add(new DefaultMutableTreeNode("UUID: %s".formatted(trade.getUuid())));


		var articleOneNode = new DefaultMutableTreeNode(articleOne.getArticleName());
		articleOneNode.add(TreeUtils.generateFields(articleOne));
		articleOneNode.add(new DefaultMutableTreeNode("Stato: %s".formatted(articleOne.getArticleState().toString())));

		var articleTwoNode = new DefaultMutableTreeNode(articleTwo.getArticleName());
		articleTwoNode.add(TreeUtils.generateFields(articleTwo));
		articleTwoNode.add(new DefaultMutableTreeNode("Stato: %s".formatted(articleTwo.getArticleState().toString())));

		var historyNode = new DefaultMutableTreeNode("Log");
		trade.getHistory()
						.forEach(history -> historyNode.add(
								new DefaultMutableTreeNode("%s %s - %s - %s".formatted(
										history.name().isPresent() ? "\u2705" : "\u274C",
										history.dateTime().format(DateTimeFormatter.ofPattern("HH:mm ~ dd/MM/yyyy")),
										history.name().isPresent() ? history.name().get() : history.error().orElseThrow(NullPointerException::new),
										history.description().orElseThrow(NullPointerException::new)
								))));

		tradeNode.add(articleOneNode);
		tradeNode.add(articleTwoNode);
		tradeNode.add(historyNode);

		return tradeNode;
	}

}
