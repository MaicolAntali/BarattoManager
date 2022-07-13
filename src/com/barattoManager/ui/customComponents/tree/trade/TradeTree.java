package com.barattoManager.ui.customComponents.tree.trade;

import com.barattoManager.manager.factory.ArticleManagerFactory;
import com.barattoManager.manager.factory.MeetManagerFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.meet.Meet;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.trade.TradeStatus;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Class used to create the {@link Trade} tree
 */
public class TradeTree extends Tree {

	private DefaultMutableTreeNode rootNode;

	/**
	 * Constructor of the class
	 *
	 * @param trades    {@link List} that contains the {@link Trade}
	 * @param user      {@link User} who has logged in
	 * @param dimension {@link Dimension} of the {@link JPanel} that contains the tree
	 */
	public TradeTree(List<Trade> trades, User user, Dimension dimension) {
		super(dimension);

		var nodeMap = new HashMap<String, ArrayList<DefaultMutableTreeNode>>();
		trades
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

	/**
	 * Constructor of the class
	 *
	 * @param trades {@link List} that contains the {@link Trade}
	 * @param user   {@link User} who has logged in
	 */
	public TradeTree(List<Trade> trades, User user) {
		this(trades, user, new Dimension(500, 290));
	}

	@Override
	protected DefaultMutableTreeNode getRootNode() {
		if (rootNode == null)
			rootNode = new DefaultMutableTreeNode("Scambi");

		return rootNode;
	}

	private DefaultMutableTreeNode createTradeNode(Trade trade) {
		Article articleOne = ArticleManagerFactory.getManager().getArticleById(trade.getArticleOneUuid()).orElseThrow(NullPointerException::new);
		Article articleTwo = ArticleManagerFactory.getManager().getArticleById(trade.getArticleTwoUuid()).orElseThrow(NullPointerException::new);
		Meet    meet       = MeetManagerFactory.getManager().getMeetByUuid(trade.getMeetUuid()).orElseThrow(NullPointerException::new);

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
		articleOneNode.add(generateFields(articleOne));
		articleOneNode.add(new DefaultMutableTreeNode("Stato: %s".formatted(articleOne.getArticleState().toString())));

		var articleTwoNode = new DefaultMutableTreeNode(articleTwo.getArticleName());
		articleTwoNode.add(generateFields(articleTwo));
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
