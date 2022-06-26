package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.MeetManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.meet.Meet;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class TradeArticle implements MenuAction {
	@Override
	public void run(RepaintEventHandler repaintEventHandler, User user, Tree tree) {

		TreeNode[] nodePath;
		try {
			nodePath = tree.getSelectedPathNode();
		} catch (NoNodeSelected e) {
			JOptionPane.showMessageDialog(tree, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		AtomicReference<String> articleUuid = new AtomicReference<>("");
		IntStream.range(0, nodePath[nodePath.length - 1].getChildCount())
				.forEach(i -> {
					if (nodePath[nodePath.length - 1].getChildAt(i).toString().startsWith("UUID:")) {
						articleUuid.set(nodePath[nodePath.length - 1].getChildAt(i).toString().split(":")[1].trim());
					}
				});

		var articleOption = ArticleManager.getInstance().getArticleById(articleUuid.get());

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
			var selectMeetDatePanel = new SelectMeetDatePanel();
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

				repaintEventHandler.fireListeners();
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

		public SelectMeetDatePanel() {
			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));

			mainPanel.add(new JLabel("Seleziona il giorno dello scambio:"));

			meetComboBox.setRenderer(new MeetComboBoxCustomRenderer());
			MeetManager.getInstance().getAvailableMeet().forEach(meetComboBox::addItem);

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