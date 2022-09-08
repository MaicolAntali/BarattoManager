package com.barattoManager.ui.mvc.viewer.tradesViewer;

import com.barattoManager.services.trade.TradeManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.menu.trades.TradeMenuController;
import com.barattoManager.ui.mvc.menu.trades.TradeMenuModel;
import com.barattoManager.ui.mvc.menu.trades.TradeMenuView;
import com.barattoManager.ui.mvc.tree.trade.TradeTreeController;
import com.barattoManager.ui.mvc.tree.trade.TradeTreeModel;
import com.barattoManager.ui.mvc.tree.trade.TradeTreeView;
import com.barattoManager.ui.utils.ControllerNames;

/**
 * Controller that handles the events of the {@link ViewerTradesView}
 */
public class ViewerTradesController implements Controller {

	private final ViewerTradesView view;

	/**
	 * Constructor of the class
	 *
	 * @param view {@link ViewerTradesView} represent the view of the controller
	 */
	public ViewerTradesController(ViewerTradesView view) {
		this.view = view;

		var tradeTreeController = new TradeTreeController(
				new TradeTreeModel(TradeManagerFactory.getManager().getTradeMap().values().stream().toList()),
				new TradeTreeView()
		);

		this.view.setTreePanel(tradeTreeController.getView().getMainJPanel());

		var tradeMenu = new TradeMenuController(
				new TradeMenuModel(tradeTreeController),
				new TradeMenuView()
		);

		this.view.setMenuPanel(tradeMenu.getView().getMainJPanel());

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public Model getModel() {
		return null;
	}

	@Override
	public View getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "backToHome")
	private void clickOnBackToHome() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_VIEWER.toString());
	}

	@ActionListenerFor(sourceField = "questionButton")
	private void clickOnQuestionButton() {
		System.out.println("Informazioni");
	}
}
