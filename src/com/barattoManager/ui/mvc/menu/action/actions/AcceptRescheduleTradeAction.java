package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.services.article.Article;
import com.barattoManager.services.meet.Meet;
import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.dialogs.select.selectMeet.SelectMeetController;
import com.barattoManager.ui.mvc.dialogs.select.selectMeet.SelectMeetModel;
import com.barattoManager.ui.mvc.dialogs.select.selectMeet.SelectMeetView;
import com.barattoManager.ui.mvc.menu.action.TradeBaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;

/**
 * TradeBaseAction used to accept and reschedule a trade
 */
public class AcceptRescheduleTradeAction extends TradeBaseAction {

	private static final String TITLE_SELECT_MEET = "Selezione Incontro";
	private static final String MESSAGE_NO_TRADE_HAS_BEEN_SELECTED = "Non è stato selezionato uno scambio. Riprovare.";

	/**
	 * Constructor of the class
	 *
	 * @param user           {@link User} who has to do the action, who has logged in
	 * @param treeController {@link TreeController}
	 */
	public AcceptRescheduleTradeAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	@Override
	public void run() {
		var selectMeetController = new SelectMeetController(
				new SelectMeetModel(MeetManagerFactory.getManager().getAvailableMeet()),
				new SelectMeetView(Meet.class)
		);

		var option = new OptionDialogDisplay()
				.setParentComponent(getTreeController().getView().getMainJPanel())
				.setMessage(selectMeetController.getView().getMainJPanel())
				.setTitle(TITLE_SELECT_MEET)
				.show();

		if (option == JOptionPane.OK_OPTION) {

			if (getTrade().isEmpty()) {
				new MessageDialogDisplay()
						.setParentComponent(getTreeController().getView().getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage(MESSAGE_NO_TRADE_HAS_BEEN_SELECTED)
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
