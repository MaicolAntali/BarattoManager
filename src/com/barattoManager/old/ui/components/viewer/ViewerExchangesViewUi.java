package com.barattoManager.old.ui.components.viewer;

import com.barattoManager.event.DataChangeListener;
import com.barattoManager.old.manager.factory.ArticleManagerFactory;
import com.barattoManager.old.manager.factory.TradeManagerFactory;
import com.barattoManager.old.sample.trade.Trade;
import com.barattoManager.old.sample.user.User;
import com.barattoManager.old.ui.components.ComponentsName;
import com.barattoManager.old.ui.customComponents.menu.factory.TradeMenuFactory;
import com.barattoManager.old.ui.customComponents.tree.Tree;
import com.barattoManager.old.ui.customComponents.tree.trade.TradeTree;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used to create a {@link JPanel} that allows the viewer to manage his {@link Trade trades}
 */
public class ViewerExchangesViewUi extends JPanel implements DataChangeListener<String, Trade> {
	private static final String HELP_MESSAGE = """
			In questa pagina puoi visualizzare i tuoi scambi
			Per effettuare un operazione su uno scambio puoi cliccare sul menu in alto al sinistra e scegliere di:
				 - Accettare una proposta di scambio;
				 - Accettare e riprogrammare una proposta di scambio.""";
	private final User user;

	private Tree tradeTree;
	private JMenuBar menu;
	private JPanel mainPanel;
	private JButton backToHomeButton;
	private JPanel centerPanel;
	private JButton questionButton;

	/**
	 * Constructor of the class
	 *
	 * @param dimension      {@link Dimension} of the {@link JPanel} to be created
	 * @param cardLayout     {@link CardLayout} object that represent the type layout
	 * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
	 * @param user           {@link User} who has logged in
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
