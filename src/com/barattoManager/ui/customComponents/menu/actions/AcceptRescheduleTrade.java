package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.manager.MeetManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.meet.Meet;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.template.TradeTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class AcceptRescheduleTrade extends TradeTemplate {

	@Override
	protected void customAction(Trade trade, Tree tree, User user) {
		var selectMeetDatePanel = new SelectMeetDatePanel();

		int resultMeetDate = JOptionPane.showOptionDialog(
				tree,
				selectMeetDatePanel,
				"Riprogramma Scambio",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				null
		);

		if (resultMeetDate == JOptionPane.OK_OPTION) {
			MeetManager.getInstance().unbookMeet(selectMeetDatePanel.getSelectedMeet().getUuid());
			MeetManager.getInstance().bookMeet(selectMeetDatePanel.getSelectedMeet().getUuid(), user.getUsername());

			chageArticleState(trade.getArticleOneUuid(), Article.State.IN_TRADE_OFFER);
			chageArticleState(trade.getArticleTwoUuid(), Article.State.IN_TRADE_OFFER);

			trade.rescheduleTrade();
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
