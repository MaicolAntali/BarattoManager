package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.services.article.Article;
import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.dialogs.selectMeet.SelectMeetController;
import com.barattoManager.ui.mvc.dialogs.selectMeet.SelectMeetModel;
import com.barattoManager.ui.mvc.dialogs.selectMeet.SelectMeetView;
import com.barattoManager.ui.mvc.menu.action.TradeBaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;

public class AcceptRescheduleTradeAction extends TradeBaseAction {

	public AcceptRescheduleTradeAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	@Override
	public void run() {
		var selectMeetController = new SelectMeetController(
				new SelectMeetModel(MeetManagerFactory.getManager().getAvailableMeet()),
				new SelectMeetView()
		);

		var option = new OptionDialogDisplay()
				.setParentComponent(getTreeController().getView().getMainJPanel())
				.setMessage(selectMeetController.getView().getMainJPanel())
				.setTitle("Selezione Incontro")
				.show();

		if (option == JOptionPane.OK_OPTION) {

			if (getTrade().isEmpty()) {
				new MessageDialogDisplay()
						.setParentComponent(getTreeController().getView().getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage("Non è stato selezionato uno scambio. Riprovare.")
						.show();
				return;
			}

			MeetManagerFactory.getManager()
					.unBookMeet(getTrade().get().getMeetUuid());

			MeetManagerFactory.getManager()
					.bookMeet(selectMeetController.getModel().getMeetSelected().getUuid(), getUser().getUsername());

			changeArticleState(getTrade().get().getArticleOneUuid(), Article.State.IN_TRADE_OFFER);
			changeArticleState(getTrade().get().getArticleTwoUuid(), Article.State.IN_TRADE_OFFER);

			getTrade().get().rescheduleTrade();
		}
	}
}
