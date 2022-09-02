package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.services.Store;
import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.services.trade.TradeManagerFactory;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.dialogs.selectArticle.SelectArticleController;
import com.barattoManager.ui.mvc.dialogs.selectArticle.SelectArticleModel;
import com.barattoManager.ui.mvc.dialogs.selectArticle.SelectArticleView;
import com.barattoManager.ui.mvc.dialogs.selectMeet.SelectMeetController;
import com.barattoManager.ui.mvc.dialogs.selectMeet.SelectMeetModel;
import com.barattoManager.ui.mvc.dialogs.selectMeet.SelectMeetView;
import com.barattoManager.ui.mvc.menu.action.BaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;
import java.time.LocalDateTime;

public class NewTradeAction extends BaseAction {


	public NewTradeAction(User user, TreeController<?> treeController) {
		super(user, treeController);
	}

	@Override
	public void run() {
		var article = ArticleManagerFactory.getManager().getArticleById(
				getUUIDFromNodes(getNodePath())
		);

		if (article.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non è stato selezionato uno scambio")
					.show();
			return;
		}

		var articlesUserCanTrade = ArticleManagerFactory.getManager()
				.getArticlesByOwnerStateCategory(getUser().getUsername(), Article.State.OPEN_OFFER, article.get().getCategoryUuid());

		if (articlesUserCanTrade.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non possiedi nessun articolo che può essere barattato con l'articolo selezionato.")
					.show();
			return;
		}

		var selectArticleController = new SelectArticleController(
				new SelectArticleModel(articlesUserCanTrade),
				new SelectArticleView()
		);

		var option = new OptionDialogDisplay()
				.setParentComponent(getTreeController().getView().getMainJPanel())
				.setMessage(selectArticleController.getView().getMainJPanel())
				.setTitle("Selezione Articolo")
				.show();

		if (option == JOptionPane.OK_OPTION) {
			var selectMeetController = new SelectMeetController(
					new SelectMeetModel(MeetManagerFactory.getManager().getAvailableMeet()),
					new SelectMeetView()
			);

			var meetOption = new OptionDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessage(selectMeetController.getView().getMainJPanel())
					.setTitle("Selezione Incontro")
					.show();

			if (meetOption == JOptionPane.OK_OPTION) {
				TradeManagerFactory.getManager().addNewTrade(
						LocalDateTime.now().plusDays(selectMeetController.getModel().getMeetSelected().getDaysBeforeExpire()),
						selectArticleController.getModel().getArticleSelected().getUuid(),
						article.get().getUuid(),
						selectMeetController.getModel().getMeetSelected().getUuid()
				);

				MeetManagerFactory.getManager().bookMeet(
						selectMeetController.getModel().getMeetSelected().getUuid(),
						Store.getLoggedUser().getUsername()
				);
			}
		}
	}
}
