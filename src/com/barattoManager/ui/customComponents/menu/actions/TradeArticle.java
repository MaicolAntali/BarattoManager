package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.factory.ArticleManagerFactory;
import com.barattoManager.manager.factory.MeetManagerFactory;
import com.barattoManager.manager.factory.TradeManagerFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.panels.SelectMeetDate;
import com.barattoManager.ui.customComponents.menu.actions.template.NodeUuidActionTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Trade article action
 */
public class TradeArticle extends NodeUuidActionTemplate {
	/**
	 * Error: no article has been selected
	 */
	private static final String ERROR_NO_ARTICLE_SELECTED = "Non è stato selezionato nessun articolo";
	/**
	 * Error: there is no article owned related to the chosen category
	 */
	private static final String ERROR_NO_OWNED_ARTICLE_IN_CATEGORY = "Non possiedi nessun articolo di questa categoria";
	/**
	 * Label: Choose an article to trade.
	 */
	public static final String CHOOSE_THE_ARTICLE_TO_TRADE = "Seleziona l'oggetto che vuoi scambiare:";

	@Override
	protected void customAction(String uuid, Tree tree, User user) {
		var articleOption = ArticleManagerFactory.getManager().getArticleById(uuid);

		if (articleOption.isEmpty()) {
			JOptionPane.showMessageDialog(tree, ERROR_NO_ARTICLE_SELECTED, "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}


		var articlesCanBeTrade = ArticleManagerFactory.getManager()
				.getArticlesByOwnerStateCategory(user.getUsername(), Article.State.OPEN_OFFER, articleOption.get().getCategoryUuid());


		if (articlesCanBeTrade.isEmpty()) {
			JOptionPane.showMessageDialog(tree, ERROR_NO_OWNED_ARTICLE_IN_CATEGORY, "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		var selectArticleToTradePanel = new SelectArticleToTradePanel(articlesCanBeTrade);
		int result = JOptionPane.showOptionDialog(
				tree,
				selectArticleToTradePanel,
				"Scambia Articolo",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				null
		);

		if (result == JOptionPane.OK_OPTION) {
			SelectMeetDate selectMeetDatePanel;

			try {
				selectMeetDatePanel = new SelectMeetDate();
			} catch (IllegalValuesException e) {
				JOptionPane.showMessageDialog(tree, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int resultMeetDate = JOptionPane.showOptionDialog(
					tree,
					selectMeetDatePanel,
					"Scambia Articolo",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (resultMeetDate == JOptionPane.OK_OPTION) {
				TradeManagerFactory.getManager().addNewTrade(
						LocalDateTime.now().plusDays(selectMeetDatePanel.getSelectedMeet().getDaysBeforeExpire()),
						selectArticleToTradePanel.getSelectedArticle().getUuid(),
						articleOption.get().getUuid(),
						selectMeetDatePanel.getSelectedMeet().getUuid()
				);

				MeetManagerFactory.getManager().bookMeet(selectMeetDatePanel.getSelectedMeet().getUuid(), user.getUsername());
			}
		}
	}

	/**
	 * JPanel used to select a article to trade
	 */
	static class SelectArticleToTradePanel extends JPanel {

		private final JComboBox<Article> articleComboBox = new JComboBox<>();


		/**
		 * {@link SelectArticleToTradePanel} costructor
		 *
		 * @param articles articles list
		 */
		public SelectArticleToTradePanel(List<Article> articles) {
			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));


			mainPanel.add(new JLabel(CHOOSE_THE_ARTICLE_TO_TRADE));

			articleComboBox.setRenderer(new ArticleComboBoxCustomRenderer());
			for (Article article : articles) {
				articleComboBox.addItem(article);
			}

			mainPanel.add(articleComboBox);

			add(mainPanel);
			setVisible(true);
		}

		/**
		 * Method used to get the selected article
		 *
		 * @return {@link Article} selected
		 */
		public Article getSelectedArticle() {
			return (Article) articleComboBox.getSelectedItem();
		}

		/**
		 * Combobox custom renderer
		 */
		static class ArticleComboBoxCustomRenderer extends BasicComboBoxRenderer {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				var item = (Article) value;

				setText(item.getArticleName());

				return this;
			}
		}
	}
}