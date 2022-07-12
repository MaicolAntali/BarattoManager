package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.factory.MeetManagerFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.panels.SelectMeetDate;
import com.barattoManager.ui.customComponents.menu.actions.template.TradeTemplate;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;

public class AcceptRescheduleTrade extends TradeTemplate {

	@Override
	protected void customAction(Trade trade, Tree tree, User user) {
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
				"Riprogramma Scambio",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				null
		);

		if (resultMeetDate == JOptionPane.OK_OPTION) {
			MeetManagerFactory.getManager().unBookMeet(selectMeetDatePanel.getSelectedMeet().getUuid());
			MeetManagerFactory.getManager().bookMeet(selectMeetDatePanel.getSelectedMeet().getUuid(), user.getUsername());

			changeArticleState(trade.getArticleOneUuid(), Article.State.IN_TRADE_OFFER);
			changeArticleState(trade.getArticleTwoUuid(), Article.State.IN_TRADE_OFFER);

			trade.rescheduleTrade();
		}

	}
}
