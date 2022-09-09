package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.exception.NullObjectException;
import com.barattoManager.services.Store;
import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.meet.Meet;
import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.services.trade.TradeManagerFactory;
import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.dialogs.select.selectArticle.SelectArticleController;
import com.barattoManager.ui.mvc.dialogs.select.selectArticle.SelectArticleModel;
import com.barattoManager.ui.mvc.dialogs.select.selectArticle.SelectArticleView;
import com.barattoManager.ui.mvc.dialogs.select.selectMeet.SelectMeetController;
import com.barattoManager.ui.mvc.dialogs.select.selectMeet.SelectMeetModel;
import com.barattoManager.ui.mvc.dialogs.select.selectMeet.SelectMeetView;
import com.barattoManager.ui.mvc.menu.action.BaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;
import java.time.LocalDateTime;

/**
 * BaseAction used to add a new trade
 */
public class NewTradeAction extends BaseAction {

	private static final String ERROR = "Errore";
	private static final String MESSAGE_NO_TRADE_HAS_BEEN_SELECTED = "Non è stato selezionato uno scambio";
	private static final String MESSAGE_YOU_DONT_HAVE_ANY_ARTICLE_TO_EXCHANGE = "Non possiedi nessun articolo che può essere barattato con l'articolo selezionato.";
	private static final String TITLE_SELECT_ARTICLE = "Selezione Articolo";

	/**
	 * Constructor of the class
	 *
	 * @param user           {@link User} who has to do the action, who has logged in
	 * @param treeController {@link TreeController}
	 */
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
					.setTitle(ERROR)
					.setMessage(MESSAGE_NO_TRADE_HAS_BEEN_SELECTED)
					.show();
			return;
		}

		var articlesUserCanTrade = ArticleManagerFactory.getManager()
				.getArticlesByOwnerStateCategory(getUser().getUsername(), Article.State.OPEN_OFFER, article.get().getCategoryUuid());

		if (articlesUserCanTrade.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle(ERROR)
					.setMessage(MESSAGE_YOU_DONT_HAVE_ANY_ARTICLE_TO_EXCHANGE)
					.show();
			return;
		}

		var selectArticleController = new SelectArticleController(
				new SelectArticleModel(articlesUserCanTrade),
				new SelectArticleView(Article.class)
		);

		var option = new OptionDialogDisplay()
				.setParentComponent(getTreeController().getView().getMainJPanel())
				.setMessage(selectArticleController.getView().getMainJPanel())
				.setTitle(TITLE_SELECT_ARTICLE)
				.show();

		if (option == JOptionPane.OK_OPTION) {
			var selectMeetController = new SelectMeetController(
					new SelectMeetModel(MeetManagerFactory.getManager().getAvailableMeet()),
					new SelectMeetView(Meet.class)
			);

			var meetOption = new OptionDialogDisplay()
					.setParentComponent(getTreeController().getView().getMainJPanel())
					.setMessage(selectMeetController.getView().getMainJPanel())
					.setTitle(TITLE_SELECT_ARTICLE)
					.show();

			if (meetOption == JOptionPane.OK_OPTION) {
				try {
					TradeManagerFactory.getManager().addNewTrade(
							LocalDateTime.now().plusDays(selectMeetController.getModel().getMeetSelected().getDaysBeforeExpire()),
							selectArticleController.getModel().getArticleSelected().getUuid(),
							article.get().getUuid(),
							selectMeetController.getModel().getMeetSelected().getUuid()
					);
				} catch (NullObjectException e) {
					new MessageDialogDisplay()
							.setParentComponent(getTreeController().getView().getMainJPanel())
							.setMessageType(JOptionPane.ERROR_MESSAGE)
							.setTitle("Errore")
							.setMessage(e.getMessage())
							.show();
					return;
				}

				try {
					MeetManagerFactory.getManager().bookMeet(
							selectMeetController.getModel().getMeetSelected().getUuid(),
							Store.getLoggedUser().getUsername()
					);
				} catch (NullObjectException e) {
					new MessageDialogDisplay()
							.setParentComponent(getTreeController().getView().getMainJPanel())
							.setMessageType(JOptionPane.ERROR_MESSAGE)
							.setTitle("Errore")
							.setMessage(e.getMessage())
							.show();
				}
			}
		}
	}
}
