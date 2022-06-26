package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class AcceptTrade implements MenuAction {
	@Override
	public void run(RepaintEventHandler repaintEventHandler, User user, Tree tree) {
		TreeNode[] nodePath;
		try {
			nodePath = tree.getSelectedPathNode();
		} catch (NoNodeSelected e) {
			JOptionPane.showMessageDialog(tree, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		AtomicReference<String> tradeUuid = new AtomicReference<>("");
		IntStream.range(0, nodePath[nodePath.length - 1].getChildCount())
				.forEach(i -> {
					if (nodePath[nodePath.length - 1].getChildAt(i).toString().startsWith("UUID:")) {
						tradeUuid.set(nodePath[nodePath.length - 1].getChildAt(i).toString().split(":")[1].trim());
					}
				});

		var tradeOptional = TradeManager.getInstance().getTradeByUuid(tradeUuid.get());

		if (tradeOptional.isEmpty()) {
			JOptionPane.showMessageDialog(tree, "Non è stato selezionato nessun articolo", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		System.out.println("...");
	}
}
