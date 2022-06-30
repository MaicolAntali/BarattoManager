package com.barattoManager.ui.customComponents.menu.actions.template;

import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import java.util.Objects;

public abstract class TradeTemplate extends NodeUuidActionTemplate {

	@Override
	protected void customAction(String uuid, Tree tree, User user) {
		var tradeOptional = TradeManager.getInstance().getTradeByUuid(uuid);

		if (tradeOptional.isEmpty()) {
			JOptionPane.showMessageDialog(tree, "Non è stato selezionato nessun articolo", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (Objects.equals(tradeOptional.get().getAnswer().getWaitingUserAnswer(), user.getUsername())) {
			JOptionPane.showMessageDialog(tree, "Devi aspettare la risposta dell'altro utente!", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		customAction(tradeOptional.get(), tree, user);
	}

	protected abstract void customAction(Trade trade, Tree tree, User user);
}
