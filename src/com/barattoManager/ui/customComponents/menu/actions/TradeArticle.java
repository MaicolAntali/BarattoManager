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
 * Class used to create a {@link NodeUuidActionTemplate} that permits to trade an {@link Article article}
 */
public class TradeArticle extends NodeUuidActionTemplate {

	private static final String ERROR_NO_ARTICLE_SELECTED = "Non è stato selezionato nessun articolo";
	private static final String ERROR_NO_OWNED_ARTICLE_IN_CATEGORY = "Non possiedi nessun articolo di questa categoria";
	private static final String CHOOSE_THE_ARTICLE_TO_TRADE = "Seleziona l'oggetto che vuoi scambiare:";

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
	 * JPanel used to select an article to trade
	 */
	static class SelectArticleToTradePanel extends JPanel {

		private final JComboBox<Article> articleComboBox = new JComboBox<>();


		/**
		 * Constructor of the class
		 *
		 * @param articles {@link List} of {@link Article} that can be trade
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