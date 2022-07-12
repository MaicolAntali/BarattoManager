package com.barattoManager.ui.components.viewer;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.manager.factory.ArticleManagerFactory;
import com.barattoManager.manager.factory.TradeManagerFactory;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.menu.factory.TradeMenuFactory;
import com.barattoManager.ui.customComponents.tree.Tree;
import com.barattoManager.ui.customComponents.tree.trade.TradeTree;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ViewerExchangesViewUi extends JPanel implements DataChangeListener<String, Trade> {
	public static final String HELP_MESSAGE = "...";
	private final User user;

	private Tree tradeTree;
	private JMenuBar menu;
	private JPanel mainPanel;
	private JButton backToHomeButton;
	private JPanel centerPanel;
	private JButton questionButton;

	/**
	 * {@link ViewerExchangesViewUi} constructor
	 *
	 * @param dimension      Dimension of JPanel
	 * @param cardLayout     {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 * @param user           {@link User}
	 */
	public ViewerExchangesViewUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
		this.user = user;
		this.tradeTree = new TradeTree(tradeMapFilter(TradeManagerFactory.getManager().getTradeMap()), user);

		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		menu = new TradeMenuFactory().createMenuObject().createMenu(user, tradeTree);
		centerPanel.add(menu, BorderLayout.NORTH);
		centerPanel.add(tradeTree);


		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));

		questionButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
				HELP_MESSAGE,
				"Help",
				JOptionPane.INFORMATION_MESSAGE));
	}

	@Override
	public void update(ConcurrentHashMap<String, Trade> updatedMap) {
		centerPanel.remove(tradeTree);
		centerPanel.remove(menu);

		this.tradeTree = new TradeTree(tradeMapFilter(updatedMap), user);
		this.menu = new TradeMenuFactory().createMenuObject().createMenu(user, tradeTree);

		centerPanel.add(menu, BorderLayout.NORTH);
		centerPanel.add(tradeTree);

		centerPanel.repaint();
		centerPanel.revalidate();
	}

	private List<Trade> tradeMapFilter(ConcurrentHashMap<String, Trade> tradeMap) {
		return tradeMap.values()
				.stream()
				.filter(trade -> Objects.equals(ArticleManagerFactory.getManager().getArticleById(trade.getArticleOneUuid()).orElseThrow(NullPointerException::new)
						.getUserNameOwner(), user.getUsername())
						|| Objects.equals(ArticleManagerFactory.getManager().getArticleById(trade.getArticleTwoUuid()).orElseThrow(NullPointerException::new)
						.getUserNameOwner(), user.getUsername())
				)
				.toList();
	}
}
