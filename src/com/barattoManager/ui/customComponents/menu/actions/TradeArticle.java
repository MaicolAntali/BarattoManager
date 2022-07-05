package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.MeetManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.meet.Meet;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.template.NodeUuidActionTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class TradeArticle extends NodeUuidActionTemplate {

	@Override
	protected void customAction(String uuid, Tree tree, User user) {
		var articleOption = ArticleManager.getInstance().getArticleById(uuid);

		if (articleOption.isEmpty()) {
			JOptionPane.showMessageDialog(tree, "Non è stato selezionato nessun articolo", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}


		var articlesCanBeTrade = ArticleManager.getInstance()
				.getArticlesByOwnerStateCategory(user.getUsername(), Article.State.OPEN_OFFER, articleOption.get().getCategoryUuid());


		if (articlesCanBeTrade.isEmpty()) {
			JOptionPane.showMessageDialog(tree, "Non possiedi nessun articolo di questa categoria", "Errore", JOptionPane.ERROR_MESSAGE);
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
			SelectMeetDatePanel selectMeetDatePanel = null;

			try {
				selectMeetDatePanel = new SelectMeetDatePanel();
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
				TradeManager.getInstance().addNewTrade(
						LocalDateTime.now().plusDays(selectMeetDatePanel.getSelectedMeet().getDaysBeforeExpire()),
						selectArticleToTradePanel.getSelectedArticle().getUuid(),
						articleOption.get().getUuid(),
						selectMeetDatePanel.getSelectedMeet().getUuid()
				);

				MeetManager.getInstance().bookMeet(selectMeetDatePanel.getSelectedMeet().getUuid(), user.getUsername());
			}
		}
	}

	static class SelectArticleToTradePanel extends JPanel {

		private final JComboBox<Article> articleComboBox = new JComboBox<>();

		public SelectArticleToTradePanel(List<Article> articles) {
			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));


			mainPanel.add(new JLabel("Seleziona l'oggetto che vuoi scambiare:"));

			articleComboBox.setRenderer(new ArticleComboBoxCustomRenderer());
			for (Article article : articles) {
				articleComboBox.addItem(article);
			}

			mainPanel.add(articleComboBox);

			add(mainPanel);
			setVisible(true);
		}

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

	static class SelectMeetDatePanel extends JPanel {

		private final JComboBox<Meet> meetComboBox = new JComboBox<>();

		public SelectMeetDatePanel() throws IllegalValuesException {
			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));

			mainPanel.add(new JLabel("Seleziona il giorno dello scambio:"));

			meetComboBox.setRenderer(new MeetComboBoxCustomRenderer());
			var meets = MeetManager.getInstance().getAvailableMeet();

			if (meets.isEmpty()) {
				throw new IllegalValuesException("Non ci sono meet disponibili");
			}

			meets.forEach(meetComboBox::addItem);

			mainPanel.add(meetComboBox);

			add(mainPanel);
			setVisible(true);
		}

		public Meet getSelectedMeet() {
			return (Meet) meetComboBox.getSelectedItem();
		}

		static class MeetComboBoxCustomRenderer extends BasicComboBoxRenderer {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				var item = (Meet) value;

				setText("%s ~ %s ~ %s ~ [%s-%s]".formatted(
						item.getCity(),
						item.getSquare(),
						item.getDay(),
						item.getStartTime(),
						item.getEndTime()
				));

				return this;
			}
		}
	}
}