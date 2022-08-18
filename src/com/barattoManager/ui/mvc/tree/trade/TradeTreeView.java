package com.barattoManager.ui.mvc.tree.trade;

import com.barattoManager.services.Store;
import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.meet.Meet;
import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.services.trade.Trade;
import com.barattoManager.services.trade.TradeStatus;
import com.barattoManager.ui.mvc.tree.TreeUtils;
import com.barattoManager.ui.mvc.tree.TreeView;

import javax.swing.tree.DefaultMutableTreeNode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TradeTreeView extends TreeView<Trade> {
	@Override
	protected void drawNodes(List<Trade> list) {

		var nodeMap = new HashMap<String, ArrayList<DefaultMutableTreeNode>>();

		list.forEach(trade -> {

			var tradeKey = trade.getTradeStatus() != TradeStatus.IN_PROGRESS
					? trade.getTradeStatus().toString()
					: Objects.equals(trade.getAnswer().getWaitingUserAnswer(), Store.getLoggedUser().getUsername())
					? "Stai attendendo risposta" : "Devi rispondere";


			if (!nodeMap.containsKey(tradeKey)) {
				nodeMap.put(tradeKey, new ArrayList<>());
			}

			nodeMap.get(tradeKey).add(createTradeNode(trade));
		});

		nodeMap.forEach((k, v) -> {
			var stateNode = new DefaultMutableTreeNode(k);
			getRootNode().add(stateNode);
			v.forEach(stateNode::add);
		});
	}

	@Override
	protected String getRootNodeName() {
		return "Scambi";
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
